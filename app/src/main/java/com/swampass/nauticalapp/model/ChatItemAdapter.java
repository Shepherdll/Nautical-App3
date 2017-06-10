package com.swampass.nauticalapp.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.swampass.nauticalapp.ChatConversationActivity;
import com.swampass.nauticalapp.HomeActivity;
import com.swampass.nauticalapp.R;

import java.util.ArrayList;

/**
 * Created by Peter on 6/7/2017.
 */

public class ChatItemAdapter extends RecyclerView.Adapter<ChatItemAdapter.ViewHolder>
{
    private ArrayList<ChatMessage> messages;
    private Context mContext;
    private DatabaseReference mRef;
    private String receivedMsg;

    public ChatItemAdapter(ArrayList<ChatMessage> messages, Context mContext,DatabaseReference mRef)
    {
        this.messages = messages;
        this.mContext = mContext;
        this.mRef = mRef;
    }

    @Override
    public ChatItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.chat_conversation_single_item, parent, false);

        // Return a new holder instance
        ChatItemAdapter.ViewHolder viewHolder = new ChatItemAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ChatItemAdapter.ViewHolder holder,  final int position) {



        //getSender
        if(messages.get(position).getUserID().equals(HomeActivity.LoggedIn_User_Email))
        {
            //Log.d("LOGGED", "getSender: ");
            holder.params.setMargins((HomeActivity.Device_Width/3),5,10,10);
            holder.text_params.setMargins(15,10,0,5);
            holder.sender.setLayoutParams(holder.text_params);
            holder.itemView.setLayoutParams(holder.params);
            holder.itemView.setBackgroundResource(R.drawable.shape_outcoming_message);
            holder.sender.setText("YOU");


         }
        else
        {
            holder.params.setMargins(10,0,(HomeActivity.Device_Width/3),10);
            holder.sender.setGravity(Gravity.START);
            holder.text_params.setMargins(60,10,0,5);
            holder.sender.setLayoutParams(holder.text_params);
            holder.itemView.setLayoutParams(holder.params);
            holder.itemView.setBackgroundResource(R.drawable.shape_incoming_message);
            holder.sender.setText(messages.get(position).getUserID());

        }


    //setMessage


            if(!messages.get(position).getUserID().equals(HomeActivity.LoggedIn_User_Email))
            {
                holder.text_params.setMargins(15,10,22,15);

                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {

                            for(DataSnapshot snapshot : dataSnapshot.getChildren())
                            {








                                receivedMsg = snapshot.child("message").getValue(String.class);

                                holder.message.setLayoutParams(holder.text_params);
                                holder.message.setText(receivedMsg);
                                holder.message.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.message.setVisibility(View.VISIBLE);
                            }

                        }



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


            }
            else
            {

                holder.text_params.setMargins(65,10,22,15);
                holder.message.setLayoutParams(holder.text_params);
                holder.message.setText(messages.get(position).getMessageText());
                holder.message.setTextColor(Color.parseColor("#FFFFFF"));
                holder.message.setVisibility(View.VISIBLE);
            }








    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    //View Holder For Recycler View
    public static class ViewHolder extends RecyclerView.ViewHolder  {
        private final TextView message, sender;
        private final LinearLayout.LayoutParams params, text_params;
        LinearLayout layout;



        public ViewHolder(final View itemView) {
            super(itemView);

            message = (TextView) itemView.findViewById(R.id.fetch_chat_messgae);
            sender = (TextView) itemView.findViewById(R.id.fetch_chat_sender);
            //date = (TextView) itemView.findViewById(R.id.date_txt);
            text_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);;
            layout = (LinearLayout) itemView.findViewById(R.id.chat_linear_layout);
        }





    }



}
