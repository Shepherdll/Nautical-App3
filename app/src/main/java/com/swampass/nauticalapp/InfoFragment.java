package com.swampass.nauticalapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    private TextView mBio;
    private TextView mInt;
    private TextView mLang;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_info,container,false);


        mBio = (TextView) view.findViewById(R.id._bio);
        mInt = (TextView)  view.findViewById(R.id.intr_txt);
        mLang = (TextView)  view.findViewById(R.id.dick_turd);
        mAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        // Inflate the layout for this fragment
        if (mAuth.getCurrentUser() != null) {


            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("bio").exists())
                    {
                        String bio = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("bio").getValue(String.class);
                        if(bio.equals(""))
                            mBio.setText(bio);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }

            });



            if (mAuth.getCurrentUser() != null) {


                mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {



                        if (dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("interests").exists())
                        {
                            String interests = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("interests").getValue(String.class);
                            if (interests.equals(""))
                                mInt.setText(interests);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                    }
                });

                if (mAuth.getCurrentUser() != null) {


                    mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("languages").exists())
                            {
                                String languages = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("languages").getValue(String.class);
                                if(languages.equals(""))
                                    mLang.setText(languages);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {


                        }
                    });
                }

            }
        }


        return inflater.inflate(R.layout.fragment_info, container, false);

    }


}
