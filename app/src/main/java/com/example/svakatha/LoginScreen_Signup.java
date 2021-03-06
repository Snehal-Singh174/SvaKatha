package com.example.svakatha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginScreen_Signup extends AppCompatActivity {
    TextView logintext;
    private static final String TAG = "LoginScreen_Signup";
    EditText editTextMail;
    EditText pass;
    EditText confirmpass;
    Button signupButton;
    String userId;
    private EditText fname_edittext;
    private EditText lname_edittext;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        logintext = (TextView)findViewById(R.id.login);
        editTextMail = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.pass);
        confirmpass = (EditText)findViewById(R.id.confirmpass);
        signupButton=(Button)findViewById(R.id.signupbutton);
        fname_edittext=(EditText)findViewById(R.id.firstname);
        lname_edittext=(EditText)findViewById(R.id.lastname);


        //Getting Firebase Auth Object
        firebaseAuth=FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen_Signup.this,Login.class);
                startActivity(intent);
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser(){
        //getting email and password from edit texts
        final String email =editTextMail.getText().toString().trim();
        final String password  = pass.getText().toString().trim();
        final String confirmpassword=confirmpass.getText().toString().trim();
        final String fname=fname_edittext.getText().toString().trim();
        final String lname=lname_edittext.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(fname)){
            Toast.makeText(this,"Please enter First Name",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(lname)){
            Toast.makeText(this,"Please enter Last Name",Toast.LENGTH_LONG).show();

            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(confirmpassword)){
            Toast.makeText(this,"Please enter confirm password",Toast.LENGTH_LONG).show();
            return;
        }
        if(!password.equals(confirmpassword)){
            Toast.makeText(this, "Please Match The Password", Toast.LENGTH_SHORT).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(LoginScreen_Signup.this,"Successfully registered",Toast.LENGTH_LONG).show();
                            //Database connection

                            userId = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = db.collection("users").document(userId);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Email",email);
                            user.put("Password",password);
                            user.put("FirstName",fname);
                            user.put("LastName",lname);
                            user.put("BodyShape","");
                            user.put("Size","");
                            user.put("PriceRange","");
                            user.put("Occupation","");
                            user.put("closetChoiceDocName","");
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(LoginScreen_Signup.this, "Database Me Aapka Password Save HO GAYA", Toast.LENGTH_SHORT).show();
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),Profile.class));
                        }else{
                            //display some message here
                            Toast.makeText(LoginScreen_Signup.this,"Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
}























