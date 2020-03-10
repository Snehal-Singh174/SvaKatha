package com.example.svakatha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MyCloset extends AppCompatActivity {

    TextView closettitle,occupation,separator;
    //ImageView topwear,bottomwear,footwear,otherwear,topwear1,bottomwear1,footwear1,topwear2,
    //bottomwear2,footwear2,topwear3,bottomwear3,footwear3;
    Button upload;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_closet);

        closettitle = (TextView)findViewById(R.id.closet_title);
        occupation = (TextView)findViewById(R.id.closet_occupation);
        separator = (TextView)findViewById(R.id.separator);

        spinner = findViewById(R.id.spinner2);
        adapter1 = ArrayAdapter.createFromResource(this, R.array.closet, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity1.class);
                startActivity(activity2Intent);


            }
        });
    }
}