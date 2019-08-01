package com.example.sellit;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ResetPasword extends AppCompatActivity {

    private EditText emailpass;
    private Button sendd;
    private ImageView close;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pasword);

        emailpass = findViewById(R.id.email_input_forgot);
        sendd = findViewById(R.id.sendpass_btn);
        close = findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Toolbar tlbr = findViewById(R.id.toolbar);
        setSupportActionBar(tlbr);

        auth = FirebaseAuth.getInstance();

        sendd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailpass.getText().toString();

                if(email.equals("")){
                    Toast.makeText(ResetPasword.this,"Please Enter Your Email",Toast.LENGTH_LONG).show();
                }else{
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(ResetPasword.this,"Please Check Your Email",Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(ResetPasword.this, Login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                                finish();

                            }else{
                                Toast.makeText(ResetPasword.this,""+ Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();

                            }

                        }
                    });

                }
            }
        });
    }
}
