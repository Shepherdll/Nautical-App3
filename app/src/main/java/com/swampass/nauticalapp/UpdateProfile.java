package com.swampass.nauticalapp;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UpdateProfile extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputLayout mBio;
    private Button mBiobtn;
    private Button mDick;
    private Button interestsBtn;
    private TextInputLayout mInts;

    //Firebase
    private DatabaseReference mRef;
    private FirebaseUser mCurrentUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //Firebase
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);


        toolbar = (Toolbar) findViewById(R.id.tToolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);

        mBio = (TextInputLayout) findViewById(R.id.bio_txt);
        mBiobtn = (Button) findViewById(R.id.bio_btn);
        mDick = (Button) findViewById(R.id.dick_btn);
        interestsBtn = (Button) findViewById(R.id.btn_change2);
        mInts = (TextInputLayout) findViewById(R.id.int_txt);



        mDick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UpdateProfile.this, ProfileActivity.class));
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        mBiobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String bio = mBio.getEditText().getText().toString();
                mRef.child("bio").setValue(bio);
                Intent bio_intent = new Intent(UpdateProfile.this, ProfileActivity.class);
                bio_intent.putExtra("bio_value", bio);
            }



        });
        interestsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String ints = mInts.getEditText().getText().toString();
                mRef.child("interests").setValue(ints);
                Intent intr_intent = new Intent(UpdateProfile.this, ProfileActivity.class);
                intr_intent.putExtra("interest_value", ints);
            }



        });
    }


}
