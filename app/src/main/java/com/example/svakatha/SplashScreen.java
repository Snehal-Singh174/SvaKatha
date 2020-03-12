package com.example.svakatha;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SplashScreen extends AppCompatActivity {

    TextView splash1;
    TextView splash2;
    TextView splash3;
    ImageView logo,logolast,logofirst;
    Animation animation,animation1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

          getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
          getSupportActionBar().hide();
        splash2 = (TextView)findViewById(R.id.splash2);
        splash3 = (TextView)findViewById(R.id.splash3);
        //logo = (ImageView)findViewById(R.id.logo);
        logofirst =(ImageView)findViewById(R.id.logo_first);
        logolast = (ImageView)findViewById(R.id.logo_last);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.logo_animation);
        animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.logo_animation_side);
        logofirst.startAnimation(animation);
        logolast.startAnimation(animation1);

        /*YoYo.with(Techniques.Tada)
                .duration(2000)
                .playOn(logo);*/

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, Login.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        },3000);
    }
}
