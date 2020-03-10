package com.example.svakatha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Swipecard extends AppCompatActivity {

        private float originalX = 0;
        private float originalY = 0;
        private float startMoveX = 0;
        private float startMoveY = 0;
        int windowwidth;
        int screenCenter;
        public RelativeLayout parentView;
        private Context context;
        ArrayList<UserDataModel> userDataModelArrayList;
        private static int index = 0;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        Map<String,String> data=new HashMap<>();
        String imageCode;
        @SuppressWarnings("deprecation")
        @SuppressLint({"NewApi", "ClickableViewAccessibility"})
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setContentView(R.layout.activity_swipecard);

                context = Swipecard.this;

                parentView = (RelativeLayout) findViewById(R.id.main_layoutview);

                windowwidth = getWindowManager().getDefaultDisplay().getWidth();

                screenCenter = windowwidth / 2;

                userDataModelArrayList = new ArrayList<>();


                getArrayData();

                final LayoutInflater inflate = (LayoutInflater) Swipecard.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View containerView = inflate.inflate(R.layout.custom_layout, null);
                //RelativeLayout relativeLayoutContainer = (RelativeLayout) containerView.findViewById(R.id.relative_container);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                containerView.setLayoutParams(layoutParams);
                addParentView(containerView, index);

                parentView.setOnTouchListener(new View.OnTouchListener()
                {
                        @SuppressLint("ClickableViewAccessibility")
                        public boolean onTouch(View view, MotionEvent event)
                        {
                                float X = event.getRawX();
                                float Y = event.getRawY();
                                float deltaX = X - startMoveX;
                                float deltaY = Y - startMoveY;
                                switch (event.getAction() & MotionEvent.ACTION_MASK)
                                {
                                        case MotionEvent.ACTION_DOWN:
                                        {
                                                startMoveX = X;
                                                startMoveY = Y;
                                        }
                                        break;

                                        case MotionEvent.ACTION_UP:
                                        {
                                                startMoveX = X;
                                                startMoveY = Y;

                                                if (Math.abs(deltaY) < 250) {
                                                        parentView.setX(originalX);
                                                        parentView.setY(originalY);

                                                } else if (deltaY > 0) {

                                                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                                                        Log.e("DOWN", "Saved" + startMoveY);
                                                        removeParentView(containerView, index);
                                                        saveUserChoiceToDb(index);
                                                        if(index==8)
                                                        {
                                                                Toast.makeText(context, "Reached End", Toast.LENGTH_SHORT).show();
                                                                index=0;
                                                        }else {
                                                                index = index + 1;
                                                                addParentView(containerView, index);
                                                        }

                                                } else {
                                                        Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                                                        Log.e("UP", "Delete" + startMoveX);
                                                        removeParentView(containerView, index);
                                                        if(index == 8){
                                                                Toast.makeText(context, "Reached End", Toast.LENGTH_SHORT).show();
                                                                index=0;
                                                        }else {
                                                                index = index + 1;
                                                                addParentView(containerView, index);
                                                        }
                                                }
                                        }
                                        break;
                                }

                                return true;
                        }
                });
        }

        private void addParentView(View containerView,int index) {
                ImageView userIMG = (ImageView) containerView.findViewById(R.id.userIMG);
                Picasso.get().load(userDataModelArrayList.get(index).getUrl()).into(userIMG);
                parentView.addView(containerView);
        }

        private void removeParentView(View containerView,int index)
        {
                parentView.removeView(containerView);
        }

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


}


