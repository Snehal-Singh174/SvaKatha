package com.example.svakatha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SplashScreen extends AppCompatActivity {

    TextView splash1;
    TextView splash2;
    TextView splash3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

          getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
          getSupportActionBar().hide();
        splash1 = (TextView)findViewById(R.id.splash1);
        splash2 = (TextView)findViewById(R.id.splash2);
        splash3 = (TextView)findViewById(R.id.splash3);

        YoYo.with(Techniques.ZoomIn)
                .duration(900)
                .playOn(splash1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, LoginScreen_Signup.class);
                startActivity(i);
            }
        },1000);
    }
}
