package com.example.svakatha.Database;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class dbHelper {
    Context context;

    dbHelper(Context context) {
        this.context = context;
    }

    dbHelper() {

    }


    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;


}