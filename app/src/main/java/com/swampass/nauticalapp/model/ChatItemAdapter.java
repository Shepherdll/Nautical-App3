package com.swampass.nauticalapp.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private final LinearLayout.LayoutParams text_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    public ChatItemAdapter(ArrayList<ChatMessage> msgs, Context dick)
    {
        messages = msgs;
        mContext = dick;
    }

    @Override
    public ChatItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.chatbubble, parent, false);

        // Return a new holder instance
        ChatItemAdapter.ViewHolder viewHolder = new ChatItemAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatItemAdapter.ViewHolder holder,  int position) {


        if(messages.get(position).getUserType() == UserType.SELF)
        {


            text_params.setMargins(15,10,22,15);

        }
        else
        {
            text_params.setMargins(65,10,22,15);

        }
        holder.message.setLayoutParams(text_params);
        holder.message.setText(messages.get(position).getMessageText());
        holder.message.setTextColor(Color.parseColor("#FFFFFF"));
        holder.message.setVisibility(View.VISIBLE);

        holder.sender.setText(messages.get(position).getUserID());


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    //View Holder For Recycler View
    public static class ViewHolder extends RecyclerView.ViewHolder  {
        private final TextView message, sender;




        public ViewHolder(final View itemView) {
            super(itemView);

            message = (TextView) itemView.findViewById(R.id.message_text);
            sender = (TextView) itemView.findViewById(R.id.sender_txt);

        }





    }



}
