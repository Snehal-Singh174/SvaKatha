package com.example.svakatha;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;

public class Shoppingpage extends Activity implements View.OnClickListener{
    ImageView imageView1;
    Button button;
    int tophone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingpage);
        imageView1=(ImageView)findViewById(R.id.imageView1);
        tophone=R.drawable.ic_launcher;
        ImageView imageView2=(ImageView)findViewById(R.id.imageView2);
        ImageView imageView3=(ImageView)findViewById(R.id.imageView3);
        ImageView imageView4=(ImageView)findViewById(R.id.imageView4);
        ImageView imageView5=(ImageView)findViewById(R.id.imageView5);
        ImageView imageView6=(ImageView)findViewById(R.id.imageView6);
        ImageView imageView7=(ImageView)findViewById(R.id.imageView7);
        button=(Button)findViewById(R.id.button);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);

        imageView5.setOnClickListener(this);imageView2.setOnClickListener(this);
        imageView6.setOnClickListener(this);
        imageView7.setOnClickListener(this);
        button.setOnClickListener(this);
    }
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.imageView2:
                imageView1.setImageResource(R.drawable.image8);
                tophone=R.drawable.ic_launcher;
                break;
            case R.id.imageView3:
                imageView1.setImageResource(R.drawable.image3);
                tophone=R.drawable.ic_launcher;
                break;
            case R.id.imageView4:
                imageView1.setImageResource(R.drawable.image4);
                tophone=R.drawable.ic_launcher;
                break;
            case R.id.imageView5:
                imageView1.setImageResource(R.drawable.image2);
                tophone=R.drawable.ic_launcher;
                break;
            case R.id.imageView6:
                imageView1.setImageResource(R.drawable.image6);
                tophone=R.drawable.ic_launcher;
                break;
            case R.id.imageView7:
                imageView1.setImageResource(R.drawable.image7);
                tophone=R.drawable.ic_launcher;
                break;
            case R.id.button:
                InputStream a=getResources().openRawResource(tophone);
                Bitmap whatever= BitmapFactory.decodeStream(a);
                try{
                    getApplicationContext().setWallpaper(whatever);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                break;
        }
    }
}