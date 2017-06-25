package com.swampass.nauticalapp.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by Peter on 5/20/2017.
 */

public class User {
    private String Name;
    private String Email;
    private String image;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;

    public User() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        mRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

        if(mAuth.getCurrentUser() != null)
        {


            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Name = dataSnapshot.child("Name").getValue(String.class);
                    Email = dataSnapshot.child("Email").getValue(String.class);
                    image = dataSnapshot.child("image").getValue(String.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });
        }

    }

    public User(String name, String email, String pic) {
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Users");
        this.Name = name;
        this.Email = email;
        this.image = pic;
    }

    public String getName() {

        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPic() {
        return image;
    }


}
