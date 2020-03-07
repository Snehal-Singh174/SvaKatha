package com.example.svakatha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SplashScreen extends AppCompatActivity {

    TextView splash1;
    TextView splash2;
    TextView splash3;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

          getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
          getSupportActionBar().hide();
        splash2 = (TextView)findViewById(R.id.splash2);
        splash3 = (TextView)findViewById(R.id.splash3);
        logo = (ImageView)findViewById(R.id.logo);

        YoYo.with(Techniques.ZoomIn)
                .duration(1000)
                .playOn(logo);
        YoYo.with(Techniques.ZoomOut)
                .delay(1000)
                .duration(1000)
                .playOn(logo);
        YoYo.with(Techniques.ZoomIn)
                .delay(2000)
                .duration(1000)
                .playOn(logo);
        /*YoYo.with(Techniques.ZoomOut)
                .delay(3000)
                .duration(1000)
                .playOn(logo);
        YoYo.with(Techniques.ZoomIn)
                .delay(4000)
                .duration(1000)
                .playOn(logo);*/

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, LoginScreen_Signup.class);
                startActivity(i);
            }
        },5000);
    }
}
