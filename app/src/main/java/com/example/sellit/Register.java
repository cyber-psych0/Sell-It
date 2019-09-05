package com.example.sellit;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class Register extends AppCompatActivity {

    private FirebaseUser user;
    DatabaseReference root_reference;
    private Button register;
    private EditText passwordd, emaill, username;
    private TextView redirecttosignin;
    private FirebaseAuth auth;
    private ProgressDialog dialog;
    private Toolbar tlbr;
    private ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.register_btn);
        emaill = findViewById(R.id.email_input_reg);
        passwordd = findViewById(R.id.pass_input_reg);
        username = findViewById(R.id.username_reg);
        redirecttosignin = findViewById(R.id.already_account);
        close = findViewById(R.id.close);

        tlbr = findViewById(R.id.reg_toolbar);
        setSupportActionBar(tlbr);

        dialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        root_reference = FirebaseDatabase.getInstance().getReference();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        redirecttosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createnewacount();
            }
        });
    }

    private void createnewacount() {
        final String Eemail = emaill.getText().toString();
        String ppass = passwordd.getText().toString();
        final String usernaaam = username.getText().toString();

        if (TextUtils.isEmpty(Eemail) || TextUtils.isEmpty(ppass)) {
            Toast.makeText(Register.this, "All Fields Are Necessary", Toast.LENGTH_SHORT).show();
        } else {

            dialog.setTitle("Creating New Account");
            dialog.setMessage("Please Wait While We Process Your Request");
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();


            auth.createUserWithEmailAndPassword(Eemail, ppass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // making some database for id and password
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                String userid = Objects.requireNonNull(auth.getCurrentUser()).getUid();

                                root_reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);


                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", userid);
                                hashMap.put("username", usernaaam);
                                hashMap.put("imageURL", "noimage");
                                hashMap.put("status","offline");
                                hashMap.put("email",Eemail);


                                root_reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Intent intent = new Intent(Register.this, Login.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    Toast.makeText(Register.this, "Please check your email for verification .....", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                } else {
                                                    Toast.makeText(Register.this, "" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                }
                                            }
                                        });
                                    }
                                });

                            } else {
                                Toast.makeText(Register.this, "" + Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                        }
                    });
        }
    }
}