package com.example.sellit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class UploadActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST = 1;

    ImageView mImageView,close;
    EditText name,price,description;
    Button choose_image_bn,upload_bn;

    private ProgressDialog dialog;

    private Uri mImageUri;
    String idd;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser user;
    private StorageReference mStorageReference;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Intent intent = getIntent();
        final String uploader_name = intent.getStringExtra("Uploader");

        mImageUri = null;

        mImageView = (ImageView)findViewById(R.id.upload_image_view);
        close = findViewById(R.id.close);

        name = (EditText)findViewById(R.id.item_name_edit_text);
        price = (EditText)findViewById(R.id.price_edit_text);
        description = (EditText)findViewById(R.id.description_edit_text);

        choose_image_bn = (Button)findViewById(R.id.choose_image_bn);
        upload_bn = (Button)findViewById(R.id.upload_bn);

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        idd = user.getUid();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("item_details");
        mStorageReference = FirebaseStorage.getInstance().getReference("item_details");

        dialog = new ProgressDialog(this);

        choose_image_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        upload_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item_name = name.getText().toString().trim();
                String item_price = price.getText().toString().trim();
                String item_description = description.getText().toString().trim();

                if(TextUtils.isEmpty(item_name)){
                    name.setError("Item Name Required");
                    name.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(item_price)){
                    price.setError("Item Price Required");
                    price.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(item_description)){
                   item_description = "No Description Provided";
                }
                uploadFile(item_name, item_price, item_description,uploader_name);


            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void openFileChooser(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            mImageUri = data.getData();

            mImageView.setImageURI(mImageUri);

        }else{
            Toast.makeText(UploadActivity.this,"Failed To Load Image",Toast.LENGTH_LONG).show();
        }
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadFile(final String item_name, final String item_price, final String item_description, final String userName)
    {
        if(mImageUri!=null) {
            dialog.setTitle("Uploading Data....");
            dialog.setMessage("Please wait....");
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

            final StorageReference fileReference = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadUrl = String.valueOf(uri);
                            //Upload upload = new Upload(item_name, item_price, item_description, downloadUrl,userName);


                            DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("item_details");
                            String id = dbref.push().getKey();

                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("item_name",item_name);
                            hashMap.put("item_price",item_price);
                            hashMap.put("item_description",item_description);
                            hashMap.put("downloaduri",downloadUrl);
                            hashMap.put("username",userName);
                            hashMap.put("id",idd);
                            assert id != null;
                            dbref.child(id).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    dialog.dismiss();
                                    Toast.makeText(UploadActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(UploadActivity.this,Navigation.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(UploadActivity.this, "Failed To Upload", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(UploadActivity.this, "Failed To Get Image Url", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(UploadActivity.this, "Failed To Upload Image", Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            Toast.makeText(UploadActivity.this,"Please Provide Image",Toast.LENGTH_SHORT).show();
        }
    }
}
