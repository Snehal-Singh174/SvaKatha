package com.example.svakatha;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;

public class Shoppingpage extends Activity implements View.OnClickListener{
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;


    Button button;
    int tophone;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingpage);
        imageView1=(ImageView)findViewById(R.id.imageView1);
        tophone=R.drawable.ic_launcher;
        imageView2 =(ImageView)findViewById(R.id.imageView2);
        imageView3=(ImageView)findViewById(R.id.imageView3);
        imageView4=(ImageView)findViewById(R.id.imageView4);
        imageView5=(ImageView)findViewById(R.id.imageView5);
        imageView6=(ImageView)findViewById(R.id.imageView6);
        imageView7=(ImageView)findViewById(R.id.imageView7);
        getDownloadURL(1,imageView2);
        getDownloadURL(2,imageView3);
        getDownloadURL(3,imageView4);
        getDownloadURL(4,imageView5);
        getDownloadURL(5,imageView6);
        getDownloadURL(6,imageView7);
        button=(Button)findViewById(R.id.button);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);

        imageView5.setOnClickListener(this);imageView2.setOnClickListener(this);
        imageView6.setOnClickListener(this);
        imageView7.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    public void getDownloadURL(final int index, final ImageView imageView){

            db.collection("ShoppingImages").document("ShoppingImageURLs").get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String downloadlink=documentSnapshot.getString("url"+index);
                            if(imageView == imageView1) {
                                Picasso.get().load(downloadlink).placeholder(R.drawable.progress_image).into(imageView1);
                                Toast.makeText(Shoppingpage.this, "Got URl Success", Toast.LENGTH_SHORT).show();
                            }else {
                                Picasso.get().load(downloadlink).placeholder(R.drawable.progress_image).into(imageView);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Shoppingpage.this, "URL Not Got", Toast.LENGTH_SHORT).show();
                            Log.i("failure","nahi hoga");
                        }
                    });
    }
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.imageView2:
                getDownloadURL(1,imageView1);
                tophone=R.drawable.ic_launcher;
                break;
            case R.id.imageView3:
                getDownloadURL(2,imageView1);
                tophone=R.drawable.ic_launcher;
                break;
            case R.id.imageView4:
                getDownloadURL(3,imageView1);
                tophone=R.drawable.ic_launcher;
                break;
            case R.id.imageView5:
                getDownloadURL(4,imageView1);
                tophone=R.drawable.ic_launcher;
                break;
            case R.id.imageView6:
               getDownloadURL(5,imageView1);
                tophone=R.drawable.ic_launcher;
                break;
            case R.id.imageView7:
            getDownloadURL(6,imageView1);
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