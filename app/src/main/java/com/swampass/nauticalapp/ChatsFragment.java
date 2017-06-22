package com.swampass.nauticalapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.swampass.nauticalapp.model.ActiveChatConvo;
import com.swampass.nauticalapp.model.User;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference mRef;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<User> users;
    private FirebaseAuth mAuth;
    private ActiveChatConvo adapter;

    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View penis = inflater.inflate(R.layout.fragment_chats, container, false);

        // Inflate the layout for this fragment
        mRef = FirebaseDatabase.getInstance().getReference("Users");
        mRef.keepSynced(true);

        mAuth = FirebaseAuth.getInstance();

        users = new ArrayList<>();
        adapter = new ActiveChatConvo(users,getContext());

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren())
                    {
                        String name = postSnapshot.child("Name").getValue(String.class);
                        String email = postSnapshot.child("Email").getValue(String.class);
                        String pic = postSnapshot.child("image").getValue(String.class);

                        users.add(new User(name,email,pic));

                    }

                    adapter.notifyDataSetChanged();
            }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Recycler View
        recyclerView = (RecyclerView) penis.findViewById(R.id.active_chats);
        //ActiveChatConvo adapter = new ActiveChatConvo(users,this);
        Context c = getContext();
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        //mLinearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();

        return penis;
    }

}
