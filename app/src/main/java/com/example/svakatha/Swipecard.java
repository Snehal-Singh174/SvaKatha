package com.example.svakatha;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

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
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
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

        LayoutInflater inflate = (LayoutInflater) Swipecard.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View containerView = inflate.inflate(R.layout.custom_layout, null);
        //RelativeLayout relativeLayoutContainer = (RelativeLayout) containerView.findViewById(R.id.relative_container);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        containerView.setLayoutParams(layoutParams);
        addParentView(containerView, index);

        parentView.setOnTouchListener(new View.OnTouchListener()
        {
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
                            index = index + 1;
                            addParentView(containerView, index);

                        } else {
                            Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                            Log.e("UP", "Delete" + startMoveX);
                            removeParentView(containerView, index);
                            index = index + 1;
                            addParentView(containerView, index);
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
        userIMG.setBackgroundResource(userDataModelArrayList.get(index).getPhoto());
/*
        TextView tvName = (TextView) containerView.findViewById(R.id.tvName);
        TextView tvTotLikes = (TextView) containerView.findViewById(R.id.tvTotalLikes);

        tvName.setText(userDataModelArrayList.get(index).getName());
        tvTotLikes.setText(userDataModelArrayList.get(index).getTotalLikes());
*/
        parentView.addView(containerView);
    }

    private void removeParentView(View containerView,int index)
    {
        parentView.removeView(containerView);
    }

    private void getArrayData() {

        UserDataModel model = new UserDataModel();
        model.setName("Cloth 1 ");
        model.setTotalLikes("3 ");
        model.setPhoto(R.drawable.image1);
        userDataModelArrayList.add(model);


        UserDataModel model2 = new UserDataModel();
        model2.setName("Cloth 2 ");
        model2.setTotalLikes("2 ");
        model2.setPhoto(R.drawable.image2);
        userDataModelArrayList.add(model2);

        UserDataModel model3 = new UserDataModel();
        model3.setName("Cloth 3 ");
        model3.setTotalLikes("33 ");
        model3.setPhoto(R.drawable.image3);
        userDataModelArrayList.add(model3);


        UserDataModel model4 = new UserDataModel();
        model4.setName("Cloth 4 ");
        model4.setTotalLikes("4 ");
        model4.setPhoto(R.drawable.image4);
        userDataModelArrayList.add(model4);


        UserDataModel model5 = new UserDataModel();
        model5.setName("Cloth 5 ");
        model5.setTotalLikes("51 ");
        model5.setPhoto(R.drawable.image5);
        userDataModelArrayList.add(model5);

        UserDataModel model6 = new UserDataModel();
        model6.setName("Cloth 6 ");
        model6.setTotalLikes("11");
        model6.setPhoto(R.drawable.image6);
        userDataModelArrayList.add(model6);


        UserDataModel model7 = new UserDataModel();
        model7.setName("Cloth 7 ");
        model7.setTotalLikes("9 ");
        model7.setPhoto(R.drawable.image7);
        userDataModelArrayList.add(model7);


        UserDataModel model8 = new UserDataModel();
        model8.setName("Cloth 8 ");
        model8.setTotalLikes("27");
        model8.setPhoto(R.drawable.image8);
        userDataModelArrayList.add(model8);

        UserDataModel model9 = new UserDataModel();
        model9.setName("Cloth 9 ");
        model9.setTotalLikes("78");
        model9.setPhoto(R.drawable.image9);
        userDataModelArrayList.add(model9);

    }
}