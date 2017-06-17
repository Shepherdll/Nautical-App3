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
    private TextInputLayout mLang;


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

        mDick = (Button) findViewById(R.id.dick_btn);

        mInts = (TextInputLayout) findViewById(R.id.int_txt);
        mLang = (TextInputLayout) findViewById(R.id.lang_txt);


        mDick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String bio = mBio.getEditText().getText().toString();
                if(bio.equals(""))
                mRef.child("bio").setValue(bio);
                Intent bio_intent = new Intent(UpdateProfile.this, ProfileActivity.class);
                bio_intent.putExtra("bio_value", bio);

                String ints = mInts.getEditText().getText().toString();
                if(!ints.equals(""))
                mRef.child("interests").setValue(ints);
                Intent intr_intent = new Intent(UpdateProfile.this, ProfileActivity.class);
                intr_intent.putExtra("interest_value", ints);

                String lang = mLang.getEditText().getText().toString();
                if(!lang.equals(""))
                mRef.child("languages").setValue(lang);
                Intent lang_intent = new Intent(UpdateProfile.this, ProfileActivity.class);
                lang_intent.putExtra("lang_value", lang);

                startActivity(new Intent(UpdateProfile.this, ProfileActivity.class));
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });



    }


}
