package com.example.svakatha;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth auth;
    Button addButton,enter,button_occupation,button_size,button_pricerange;

    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    ProgressBar progressBar;
    DrawerLayout drawer;
    ArrayAdapter<CharSequence> adapter1;
    ArrayAdapter<CharSequence> adapter2;
    ArrayAdapter<CharSequence> adapter3;
    ArrayAdapter<CharSequence> adapter4;
    String bodyShapeData;
    TextView profile_textView;
    TextView name;
    NavigationView navigationView;
    View headerView;
    TextView navUsername;
    TextView navProfileName;
    ProgressBar progressBar_drawer1;
    TextView navProgressStatus;
    FirebaseDatabase firebaseDatabase;
    boolean Status1=true;
    boolean Status2=true;
    boolean Status3=true;
    boolean Status4=true;
    ImageButton imagebtn1,imagebtn2,imagebtn3,imagebtn4,imagebtn5,imagebtn6;
    ImageButton occupation_1,occupation_2,occupation_3;
    ImageButton size_1,size_2,size_3,size_4;
    ImageButton pricerange_1,pricerange_2,pricerange_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profile_textView = (TextView) findViewById(R.id.profilephoto);
        navigationView = findViewById(R.id.nav_view);
        enter =(Button)findViewById(R.id.button1);
        button_occupation=(Button)findViewById(R.id.button2);
        button_size=(Button)findViewById(R.id.button3);
        button_pricerange=(Button)findViewById(R.id.button4);

        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.name);
        navProfileName = headerView.findViewById(R.id.photo_drawer);
        progressBar_drawer1 = headerView.findViewById(R.id.progressBar_drawer);
        navProgressStatus = headerView.findViewById(R.id.progres_text);
        name = findViewById(R.id.name);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        //BodyShape items
        imagebtn1 = (ImageButton)findViewById(R.id.image_btn1);
        imagebtn2 = (ImageButton)findViewById(R.id.image_btn2);
        imagebtn3 = (ImageButton)findViewById(R.id.image_btn3);
        imagebtn4 = (ImageButton)findViewById(R.id.image_btn4);
        imagebtn5 = (ImageButton)findViewById(R.id.image_btn5);
        imagebtn6 = (ImageButton)findViewById(R.id.image_btn6);
        //Occupation items
        occupation_1 =(ImageButton) findViewById(R.id.occupation_student);
        occupation_2 =(ImageButton)findViewById(R.id.occupation_business);
        occupation_3 =(ImageButton)findViewById(R.id.occupation_artist);
        //Size Items
        size_1=(ImageButton)findViewById(R.id.s_1);
        size_2=(ImageButton)findViewById(R.id.s_2);
        size_3=(ImageButton)findViewById(R.id.s_3);
        size_4=(ImageButton)findViewById(R.id.s_4);
        //Price Range items
        pricerange_1=(ImageButton)findViewById(R.id.image_prize1);
        pricerange_2=(ImageButton)findViewById(R.id.image_prize2);
        pricerange_3=(ImageButton)findViewById(R.id.image_prize3);



        DatabaseReference databaseReference = firebaseDatabase.getReference(auth.getCurrentUser().getUid());

        /*spinner1 = findViewById(R.id.spinner1);
        adapter1 = ArrayAdapter.createFromResource(this, R.array.body, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        spinner2 = findViewById(R.id.spinner2);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.occupation, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);


        spinner3 = findViewById(R.id.spinner3);
        adapter3 = ArrayAdapter.createFromResource(this, R.array.size, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        spinner4 = findViewById(R.id.spinner4);
        adapter4 = ArrayAdapter.createFromResource(this, R.array.price, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
//        navigationView.setNavigationItemSelectedListener(this);
        SpannableString content = new SpannableString("Shop your Design");
        content.setSpan(new

                UnderlineSpan(), 0, content.length(), 0);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.pick, R.id.shop, R.id.report,
                R.id.style_suggestion, R.id.community, R.id.closet)
                .setDrawerLayout(drawer)
                .build();

        /*NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/

        updateProfileText();
        updateProgressBar();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentID = auth.getCurrentUser().getUid();
        db.collection("users").document(currentID)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot.exists()) {
                            String BodyShape = documentSnapshot.getString("BodyShape");
                            String Occupation = documentSnapshot.getString("Occupation");
                            String PriceRange = documentSnapshot.getString("PriceRange");
                            String Size = documentSnapshot.getString("Size");
                            /*spinner1.setSelection(adapter1.getPosition(BodyShape));
                            spinner2.setSelection(adapter2.getPosition(Occupation));
                            spinner3.setSelection(adapter3.getPosition(Size));
                            spinner4.setSelection(adapter4.getPosition(PriceRange));*/
                        }
                    }
                });

        /*spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    String currentID = auth.getCurrentUser().getUid();
                    DocumentReference documentReference = db.collection("users").document(currentID);
                    String bodyShapeData;
                    bodyShapeData = spinner1.getSelectedItem().toString();
                    ((TextView) selectedItemView).setTextColor(Color.WHITE);
                    Map<String, Object> user = new HashMap<>();
                    user.put("BodyShape", bodyShapeData);
                        if(!Status1){
                        documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                updateProgressBar();
                            }
                        });
                    }
                    Status1=false;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String Occupation;
                //Occupation=spinner2.getItemAtPosition(position).toString();
                Occupation = spinner2.getSelectedItem().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String currentID = auth.getCurrentUser().getUid();
                DocumentReference documentReference = db.collection("users").document(currentID);
                ((TextView) selectedItemView).setTextColor(Color.WHITE);
                Map<String, Object> user = new HashMap<>();
                user.put("Occupation", Occupation);
                if(!Status2)
                {
                    documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                          updateProgressBar();
                        }
                    });
                }
                Status2=false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String Size;
                //PriceRange=spinner4.getItemAtPosition(position).toString();
                Size = spinner3.getSelectedItem().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String currentID = auth.getCurrentUser().getUid();
                DocumentReference documentReference = db.collection("users").document(currentID);
//                ((TextView) selectedItemView).setTextColor(Color.WHITE);

                Map<String, Object> user = new HashMap<>();
                user.put("Size", Size);
                if(!Status3)
                {
                    documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            updateProgressBar();
                        }
                    });
                }
                Status3=false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String PriceRange;
                //PriceRange=spinner4.getItemAtPosition(position).toString();
                PriceRange = spinner4.getSelectedItem().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String currentID = auth.getCurrentUser().getUid();
                DocumentReference documentReference = db.collection("users").document(currentID);
             //   ((TextView) selectedItemView).setTextColor(Color.WHITE);

                Map<String, Object> user = new HashMap<>();
                user.put("PriceRange", PriceRange);
                if(!Status4)
                {
                    documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            updateProgressBar();
                        }
                    });
                }
                Status4=false;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });*/


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Body Shape button on click listener
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout linearLayout = findViewById(R.id.bodyshape_spinner);
                linearLayout.setVisibility(View.VISIBLE);
                        imagebtn1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                enter.setText("Hourglass");
                                linearLayout.setVisibility(View.GONE);

                            }
                        });
                        imagebtn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                enter.setText("Pear");
                                linearLayout.setVisibility(View.GONE);
                            }
                        });
                        imagebtn3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                enter.setText("Inverted Triangle");
                                linearLayout.setVisibility(View.GONE);
                            }
                        });
                        imagebtn4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                enter.setText("Oval");
                                linearLayout.setVisibility(View.GONE);
                            }
                        });
                        imagebtn5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                enter.setText("Triangle");
                                linearLayout.setVisibility(View.GONE);
                            }
                        });
                        imagebtn6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                enter.setText("Athletic");
                                linearLayout.setVisibility(View.GONE);
                            }
                        });

                }
            });
        //Occupation button on click listener
        button_occupation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout linearLayout = findViewById(R.id.occupation_spinner);
                linearLayout.setVisibility(View.VISIBLE);
                occupation_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button_occupation.setText("Student");
                        linearLayout.setVisibility(View.GONE);
                    }
                });
                occupation_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button_occupation.setText("Business");
                        linearLayout.setVisibility(View.GONE);
                    }
                });
                occupation_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button_occupation.setText("Artist");
                        linearLayout.setVisibility(View.GONE);
                    }
                });
            }
        });
        //Size button on click listener
        button_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout linearLayout = findViewById(R.id.size_spinner);
                linearLayout.setVisibility(View.VISIBLE);
                size_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button_size.setText("S");
                        linearLayout.setVisibility(View.GONE);
                    }
                });
                size_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button_size.setText("L");
                        linearLayout.setVisibility(View.GONE);
                    }
                });
                size_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button_size.setText("XL");
                        linearLayout.setVisibility(View.GONE);
                    }
                });
                size_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button_size.setText("XXL");
                        linearLayout.setVisibility(View.GONE);
                    }
                });
            }
        });
        //Price Range button on click listener
        button_pricerange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout linearLayout = findViewById(R.id.pricerange_spinner);
                linearLayout.setVisibility(View.VISIBLE);
                pricerange_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button_pricerange.setText("Below 5000");
                        linearLayout.setVisibility(View.GONE);
                    }
                });
                pricerange_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button_pricerange.setText("5000-9000");
                        linearLayout.setVisibility(View.GONE);
                    }
                });
                pricerange_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button_pricerange.setText("Above 9000");
                        linearLayout.setVisibility(View.GONE);
                    }
                });
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.pick:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PickFragment()).commit();
                break;

            case R.id.shop:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ShopFragment()).commit();
                break;

            case R.id.report:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ReportFragment()).commit();
                break;

            case R.id.style_suggestion:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Swipecard1()).commit();
                break;

            case R.id.community:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CommunityFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Bottom Navigation Menu Selector
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.shop:
                    Intent intent = new Intent(Profile.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.community:
                    Intent intent1 = new Intent(Profile.this, Swipecard.class);
                    startActivity(intent1);
                    break;
                case R.id.today_pick:
                    Intent intent2 = new Intent(Profile.this, MainActivity1.class);
                    startActivity(intent2);
                    break;
                case R.id.profile:
                    Intent intent3 = new Intent(Profile.this, Profile.class);
                    startActivity(intent3);
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

    public void updateProgressBar() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentID = auth.getCurrentUser().getUid();
        final DocumentReference documentReference = db.collection("users").document(currentID);
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String BodyShape = documentSnapshot.getString("BodyShape");
                            String Occupation = documentSnapshot.getString("Occupation");
                            String PriceRange = documentSnapshot.getString("PriceRange");
                            String Size = documentSnapshot.getString("Size");
                            int progressStatus = 100;
                            if ((BodyShape == "") || (BodyShape.equals("Null"))) {
                                progressStatus = progressStatus - 25;
                            }
                            if ((Occupation == "") || (Occupation.equals("Null"))) {
                                progressStatus = progressStatus - 25;
                            }
                            if ((PriceRange == "") || (PriceRange.equals("Null"))) {
                                progressStatus = progressStatus - 25;
                            }
                            if ((Size == "") || (Size.equals("Null"))) {
                                progressStatus = progressStatus - 25;
                            }
                            progressBar.setProgress(progressStatus);
                            progressBar_drawer1.setProgress(progressStatus);
                            navProgressStatus.setText("Profile Completed " + progressStatus + "%");
                            /*spinner1.setSelection(adapter1.getPosition(BodyShape));
                            spinner2.setSelection(adapter2.getPosition(Occupation));
                            spinner3.setSelection(adapter3.getPosition(Size));
                            spinner4.setSelection(adapter4.getPosition(PriceRange));*/
                        }
                    }
                });
    }


    public void updateProfileText() {
        String currentID = auth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference documentReference = db.collection("users").document(currentID);
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String BodyShape = documentSnapshot.getString("BodyShape");
                        String Occupation = documentSnapshot.getString("Occupation");
                        String PriceRange = documentSnapshot.getString("PriceRange");
                        String Size = documentSnapshot.getString("Size");
                        String firstname = documentSnapshot.getString("FirstName");
                        String lastname = documentSnapshot.getString("LastName");
                        String firstletter = String.valueOf(firstname.charAt(0));
                        String lastletter = String.valueOf(lastname.charAt(0));
                        String finalProfileText = firstletter.concat(lastletter).toUpperCase();
                        profile_textView.setText(finalProfileText);
                        name.setText(firstname);
                        navUsername.setText(firstname);
                        navProfileName.setText(finalProfileText);
                        //navProgressStatus.setText("Profile Completed " + progressStatus + "%");
                        /*spinner1.setSelection(adapter1.getPosition(BodyShape));
                        spinner2.setSelection(adapter2.getPosition(Occupation));
                        spinner3.setSelection(adapter3.getPosition(Size));
                        spinner4.setSelection(adapter4.getPosition(PriceRange));*/
                    }
                });
    }


}