package com.example.svakatha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener{

    TextView logintext;
    EditText editTextMail;
    EditText pass;
    EditText confirmpass;
    Button signupButton;
    String userId;
    /*private static final String KEY_EMAIL= "email";
    private static final String KEY_PASSWORD= "password";
    private static final String TAG = "LoginScreen";*/
    //private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        logintext = (TextView)findViewById(R.id.logintext);
        editTextMail = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.pass);
        confirmpass = (EditText)findViewById(R.id.confirmpass);
        signupButton=(Button)findViewById(R.id.signupbutton);
        //Getting Firebase Auth Object
        firebaseAuth=FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        signupButton.setOnClickListener(this);
    }

    private void registerUser(){

        //getting email and password from edit texts
        final String email =editTextMail.getText().toString().trim();
        final String password  = pass.getText().toString().trim();
        /*final Map<String,Object> userData=new HashMap<>();
        userData.put(KEY_EMAIL,email);
        userData.put(KEY_PASSWORD,password);*/
        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
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
                            Toast.makeText(LoginScreen.this,"Successfully registered",Toast.LENGTH_LONG).show();
                            userId = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = db.collection("users").document(userId);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Email",email);
                            user.put("Password",password);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(LoginScreen.this, "Database Me Aapka Password Save HO GAYA", Toast.LENGTH_SHORT).show();
                                }
                            });
                            /*db.document("InFo/Details").set(userData)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(LoginScreen.this, "Database Me Aapka Password Save HO GAYA", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(LoginScreen.this, "Not Saved", Toast.LENGTH_SHORT).show();
                                        }
                                    });*/
//                            Intent i = new Intent(LoginScreen.this,Profile.class);
//                            startActivity(i);
                        }else{
                            //display some message here
                            Toast.makeText(LoginScreen.this,"Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View v) {
        //registerUser();
        Intent i = new Intent(LoginScreen.this,Profile.class);
        startActivity(i);

    }
}























