package com.swampass.nauticalapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
public class EditInfoFragment extends Fragment {

    private TextInputLayout mBio;
    private TextInputLayout mInt;
    private TextInputLayout mLang;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private Button mSave_Btn;
    private ViewPager mViewpager;
    private SectionPagerAdapter mSecPageAdapter;

    public EditInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_edit_info,container,false);



        mBio = (TextInputLayout)   view.findViewById(R.id.edit_bio);
        mInt = (TextInputLayout)   view.findViewById(R.id.edit_intr);
        mLang = (TextInputLayout)  view.findViewById(R.id.edit_lang);
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mSave_Btn = (Button) view.findViewById(R.id.save_btn);

        mSave_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String bio = mBio.getEditText().getText().toString();
                if(!bio.equals(""))
                    mRef.child("bio").setValue(bio);

                String ints = mInt.getEditText().getText().toString();
                if(!ints.equals(""))
                    mRef.child("interests").setValue(ints);


                String lang = mLang.getEditText().getText().toString();
                if(!lang.equals(""))
                    mRef.child("languages").setValue(lang);





            }
        });


        return inflater.inflate(R.layout.fragment_edit_info, container, false);

    }


}
