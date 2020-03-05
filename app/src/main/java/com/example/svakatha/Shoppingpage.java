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

    public void getDownloadURL(final int index){

            db.collection("ShoppingImages").document("ShoppingImageURLs").get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String downloadlink=documentSnapshot.getString("url"+index);
                           // Picasso.get().load(downloadlink).into(imageView1);
                            Toast.makeText(Shoppingpage.this, "Got URl Success", Toast.LENGTH_SHORT).show();
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
                getDownloadURL(1);
               Picasso.get().load(downloadlink).into(imageView1);
                tophone=R.drawable.ic_launcher;
                break;
            case R.id.imageView3:
                db.collection("ShoppingImages").document("ShoppingImageURLs").get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Picasso.get().load(documentSnapshot.getString("url"+2)).into(imageView1);
                            }
                        });
                //imageView1.setImageResource(R.drawable.image3);

               // Picasso.get().load(getDownloadURL(2)).into(imageView1);
                tophone=R.drawable.ic_launcher;
                break;
            case R.id.imageView4:
                db.collection("ShoppingImages").document("ShoppingImageURLs").get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Picasso.get().load(documentSnapshot.getString("url"+3)).into(imageView1);
                            }
                        });
            //    Picasso.get().load(getDownloadURL(3)).into(imageView1);
                //imageView1.setImageResource(R.drawable.image4);
                tophone=R.drawable.ic_launcher;
                break;
            case R.id.imageView5:
                db.collection("ShoppingImages").document("ShoppingImageURLs").get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Picasso.get().load(documentSnapshot.getString("url"+4)).into(imageView1);
                            }
                        });
            //    Picasso.get().load(getDownloadURL(4)).into(imageView1);
                //imageView1.setImageResource(R.drawable.image2);
                tophone=R.drawable.ic_launcher;
                break;
            case R.id.imageView6:
                db.collection("ShoppingImages").document("ShoppingImageURLs").get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Picasso.get().load(documentSnapshot.getString("url"+5)).into(imageView1);
                            }
                        });
             //   Picasso.get().load(getDownloadURL(5)).into(imageView1);
                //imageView1.setImageResource(R.drawable.image6);
                tophone=R.drawable.ic_launcher;
                break;
            case R.id.imageView7:
                db.collection("ShoppingImages").document("ShoppingImageURLs").get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Picasso.get().load(documentSnapshot.getString("url"+6)).into(imageView1);
                            }
                        });
             //   Picasso.get().load(getDownloadURL(6)).into(imageView1);
                //imageView1.setImageResource(R.drawable.image7);
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