package com.swampass.nauticalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.swampass.nauticalapp.model.ActiveChatConvo;
import com.swampass.nauticalapp.model.User;

import java.util.ArrayList;


public class ConnectionsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager mViewPager;
    private TextView person_name,person_email;

    private ConnectionsPagerAdapter mConnecPager;
    private TabLayout mTablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_connections);



        //Tabs
        mViewPager = (ViewPager) findViewById(R.id.main_tabpager);
        mConnecPager = new ConnectionsPagerAdapter(getSupportFragmentManager());
        mTablayout = (TabLayout) findViewById(R.id.main_tabs);

        mTablayout.setupWithViewPager(mViewPager);

        mViewPager.setAdapter(mConnecPager);



        //VIEWS

        toolbar = (Toolbar) findViewById(R.id.tToolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);

        ImageView profileActivity = (ImageView) toolbar.findViewById(R.id.action_profile);
        ImageView homeActivity = (ImageView) toolbar.findViewById(R.id.action_home);

        profileActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent profileActivity = new Intent(ConnectionsActivity.this, ProfileActivity.class);
                startActivity(profileActivity);
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }

        });

        homeActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent home = new Intent(ConnectionsActivity.this, HomeActivity.class);
                startActivity(home);
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }

        });
    }







    }
