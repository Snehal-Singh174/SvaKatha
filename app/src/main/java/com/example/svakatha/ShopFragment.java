package com.example.svakatha;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ShopFragment extends Fragment {
    private static final String TAG = ShopFragment.class.getSimpleName();

    //Context context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootview = inflater.inflate(R.layout.shop,container,false);
      //  context = rootview.getContext();

        Button pay = (Button)rootview.findViewById(R.id.button1);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(),RazorPay.class);
                startActivity(i);
                Log.e(TAG,"ERRor");
            }
        });
        return rootview;
    }
}
