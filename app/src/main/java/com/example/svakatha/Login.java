package com.example.svakatha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    TextView login_text;
    EditText login_email;
    EditText login_pass;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        login_text = (TextView)findViewById(R.id.login_text);
        login_email = (EditText)findViewById(R.id.login_email);
        login_pass = (EditText)findViewById(R.id.login_pass);
        login = (Button)findViewById(R.id.login_button);
    }
}
