package com.swampass.nauticalapp.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swampass.nauticalapp.ChatConversationActivity;
import com.swampass.nauticalapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 6/6/2017.
 */

public class ActiveChatConvo extends RecyclerView.Adapter<ActiveChatConvo.ViewHolder>
{
    private ArrayList<User> mUsers;
    private Context mContext;

    public ActiveChatConvo(ArrayList<User> users,Context dick) {
        mUsers = users;
        mContext = dick;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ActiveChatConvo.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.chat_single_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        User user = mUsers.get(position);

        // Set item views based on your views and data model
        TextView name = holder.mItemName;
        name.setText(user.getName());
        TextView description = holder.mItemDescription;
        description.setText(user.getEmail());
        ImageView pic = holder.mItemImage;
        Picasso.with(pic.getContext()).load(Uri.parse(user.getPic())).into(pic);

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mItemImage;
        private TextView mItemName;
        private TextView mItemDescription;
        private LinearLayout layout;
        final LinearLayout.LayoutParams params;


        public ViewHolder(View v) {
            super(v);

            mItemImage = (ImageView) v.findViewById(R.id.chat_persion_image);
            mItemName = (TextView) v.findViewById(R.id.chat_persion_name);
            mItemDescription = (TextView) v.findViewById(R.id.chat_persion_email);
            layout = (LinearLayout) itemView.findViewById(R.id.show_chat_single_item_layout);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Intent showChatIntent = new Intent(context, ChatConversationActivity.class);
            //showPhotoIntent.putExtra(PHOTO_KEY, mPhoto);
            context.startActivity(showChatIntent);
        }
    }
}
