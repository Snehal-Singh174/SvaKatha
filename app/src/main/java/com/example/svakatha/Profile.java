package com.example.svakatha;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth auth;
    private Button addButton;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    ProgressBar progressBar;
    ArrayAdapter<CharSequence> adapter1;
    ArrayAdapter<CharSequence> adapter2;
    ArrayAdapter<CharSequence> adapter3;
    ArrayAdapter<CharSequence> adapter4;
    String bodyShapeData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        addButton=(Button)findViewById(R.id.button2);
        auth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
        spinner1 = findViewById(R.id.spinner1);
        adapter1 = ArrayAdapter.createFromResource(this,R.array.body,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        spinner2 = findViewById(R.id.spinner2);
        adapter2 = ArrayAdapter.createFromResource(this,R.array.occupation,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);


        spinner3 = findViewById(R.id.spinner3);
        adapter3 = ArrayAdapter.createFromResource(this,R.array.size,android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        spinner4 = findViewById(R.id.spinner4);
        adapter4 = ArrayAdapter.createFromResource(this,R.array.price,android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);


        SpannableString content = new SpannableString("Shop your Design");
        content.setSpan(new UnderlineSpan(),0,content.length(),0);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView,navController);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        updateProgressBar();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bodyShapeData = spinner1.getSelectedItem().toString();


                String Occupation;
                Occupation = spinner2.getSelectedItem().toString();
                String Size;
                Size = spinner3.getSelectedItem().toString();
                String PriceRange;
                PriceRange = spinner4.getSelectedItem().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String currentID= auth.getCurrentUser().getUid();
                DocumentReference documentReference = db.collection("users").document(currentID);


                Map<String,Object> user = new HashMap<>();
                user.put("BodyShape",bodyShapeData);
                user.put("Occupation",Occupation);
                user.put("Size",Size);
                user.put("PriceRange",PriceRange);
                documentReference.update(user);
                //documentReference.set(user, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                  //  @Override
                   // public void onSuccess(Void aVoid) {
                     //   Toast.makeText(Profile.this, "Database Me Aapka Details Save HO GAYA", Toast.LENGTH_SHORT).show();
                   // }
                //});
                    updateProgressBar();
            }
        });
    }

    //Bottom Navigation Menu Selector
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch(item.getItemId()){
                case R.id.shop:
                    selectedFragment = new ShopFragment();
                    break;
                case R.id.community:
                    selectedFragment = new CommunityFragment();
                    break;
                case R.id.today_pick:
                    selectedFragment = new PickFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,selectedFragment).commit();
            return true;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void updateProgressBar(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentID= auth.getCurrentUser().getUid();
        final DocumentReference documentReference = db.collection("users").document(currentID);
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String BodyShape=documentSnapshot.getString("BodyShape");
                            String Occupation=documentSnapshot.getString("Occupation");
                            String PriceRange=documentSnapshot.getString("PriceRange");
                            String Size=documentSnapshot.getString("Size");
                            int progressStatus=100;
                            if(BodyShape==""){
                                progressStatus=progressStatus-25;
                            }
                            if(Occupation==""){
                                progressStatus=progressStatus-25;
                            }
                            if(PriceRange==""){
                                progressStatus=progressStatus-25;
                            }
                            if(Size==""){
                                progressStatus=progressStatus-25;
                            }
                            progressBar.setProgress(progressStatus);
                            spinner1.setSelection(adapter1.getPosition(BodyShape));

                            spinner2.setSelection(adapter2.getPosition(Occupation));

                            spinner3.setSelection(adapter3.getPosition(PriceRange));

                            spinner4.setSelection(adapter4.getPosition(Size));
                        }
                    }
                });

    }
}
