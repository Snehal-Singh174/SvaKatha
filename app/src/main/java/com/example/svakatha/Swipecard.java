package com.example.svakatha;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.svakatha.listeners.SetUrlListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Swipecard extends AppCompatActivity implements SetUrlListener {

        int windowwidth;
        int screenCenter;
        private Context context;
        ArrayList<UserDataModel> userDataModelArrayList;
        private static int index=0;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        Map<String,String> data=new HashMap<>();
        String imageCode;
        public ImageButton imgb1,imgb2;

        @SuppressWarnings("deprecation")
        @SuppressLint({"NewApi", "ClickableViewAccessibility"})
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setContentView(R.layout.custom_layout);

                imgb1 = findViewById(R.id.imgbut1);
                imgb2 = findViewById(R.id.imgbut2);

                context = Swipecard.this;


                windowwidth = getWindowManager().getDefaultDisplay().getWidth();

                screenCenter = windowwidth / 2;

                userDataModelArrayList = new ArrayList<>();


                getArrayData();

                imgb1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                                removeParentView(index);
                                if (index < 8) {
                                        saveUserChoiceToDb(index);
                                }
                                if (index == 8) {
                                        Toast.makeText(context, "Reached End", Toast.LENGTH_SHORT).show();
                                        index = 8;
                                        addParentView(index);
                                } else {
                                        index = index + 1;
                                        addParentView(index);
                                }


                        }
                });

                imgb2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                                removeParentView( index);
                                if (index == 8) {
                                        Toast.makeText(context, "Reached End", Toast.LENGTH_SHORT).show();
                                        index = 8;
                                        addParentView(index);
                                } else {
                                        index = index + 1;
                                        addParentView(index);
                                }


                        }
                });

        }
        private void addParentView(int index) {
                ImageView userIMG = (ImageView)findViewById(R.id.userIMG);
                Picasso.get().load(userDataModelArrayList.get(index).getUrl()).into(userIMG);

        }

        private void removeParentView(int index)
        {
        }

        /**
         * function to populate userDataModelArrayList
         */
        private void getArrayData() {

                final UserDataModel model = new UserDataModel();
                model.setName("Cloth 1 ");
                model.setImageCode("1");
                settingURL(model,1);
                userDataModelArrayList.add(model);


                UserDataModel model2 = new UserDataModel();
                model2.setName("Cloth 2 ");
                model2.setImageCode("2");
                settingURL(model2,2);
                userDataModelArrayList.add(model2);

                UserDataModel model3 = new UserDataModel();
                model3.setName("Cloth 3 ");
                model3.setImageCode("3");
                settingURL(model3,3);
                userDataModelArrayList.add(model3);


                UserDataModel model4 = new UserDataModel();
                model4.setName("Cloth 4 ");
                model4.setImageCode("4");
                settingURL(model4,4);
                userDataModelArrayList.add(model4);


                UserDataModel model5 = new UserDataModel();
                model5.setName("Cloth 5 ");
                model5.setImageCode("5");
                settingURL(model5,5);
                userDataModelArrayList.add(model5);

                UserDataModel model6 = new UserDataModel();
                model6.setName("Cloth 6 ");
                model6.setImageCode("6");
                settingURL(model6,6);
                userDataModelArrayList.add(model6);


                UserDataModel model7 = new UserDataModel();
                model7.setName("Cloth 7 ");
                model7.setImageCode("7");
                settingURL(model7,7);
                userDataModelArrayList.add(model7);


                UserDataModel model8 = new UserDataModel();
                model8.setName("Cloth 8 ");
                model8.setImageCode("8");
                settingURL(model8,8);
                userDataModelArrayList.add(model8);



                UserDataModel model9 = new UserDataModel();
                model9.setName("Cloth 9 ");
                model9.setImageCode("9");
                settingURL(model9,9);
                userDataModelArrayList.add(model9);

        }

        public void settingURL(final UserDataModel model, final int i){
                db.collection("Images").document("ImageURLs").get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        String durl=documentSnapshot.getString("url"+i);
                                        model.setUrl(durl);

                                        if(i==1){
                                                onFirstUrlSet();
                                        }

                                        // Log.i("Hi",durl);
                                }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                                        Log.i("hi",e.toString());
                                }
                        });
        }

        public void saveUserChoiceToDb(int index){
                String uId=auth.getCurrentUser().getUid();
                imageCode=userDataModelArrayList.get(index).getImageCode();
                // Log.i("hi",imageCode);
                ChoiceModel choiceModel=new ChoiceModel();
                choiceModel.setChoice(imageCode);
                //data.put("userchoice",imageCode);
                //db.collection("users").document(uId).set(data, SetOptions.merge() );
                db.collection("users").document(uId).collection("Choices").document().set(choiceModel);
                //db.collection("users").document(uId).set(choiceModel, SetOptions.merge() );
        }


        @Override
        public void onFirstUrlSet() {
                addParentView(index);
        }
}


