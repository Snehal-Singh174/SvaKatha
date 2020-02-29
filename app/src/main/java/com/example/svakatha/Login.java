package com.example.svakatha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText nemail1,npassword1;
    Button nloginbtn;
    TextView nsignip;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nemail1 = findViewById(R.id.login_email);
        npassword1 = findViewById(R.id.login_pass);
        nloginbtn = findViewById(R.id.login_button);

        fAuth = FirebaseAuth.getInstance();

        nloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email2 = nemail1.getText().toString().trim();
                String password1 = npassword1.getText().toString().trim();

                if(TextUtils.isEmpty(email2)){
                    nemail1.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password1)){
                    npassword1.setError("Password is required");
                    return;
                }
                if(password1.length()<6){
                    npassword1.setError("Password Must be more than 6 character");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email2,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Profile.class));

                        }else{
                            Toast.makeText(Login.this,"Error! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
