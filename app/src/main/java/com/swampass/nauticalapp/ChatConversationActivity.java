package com.swampass.nauticalapp;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.swampass.nauticalapp.model.ActiveChatConvo;
import com.swampass.nauticalapp.model.ChatItemAdapter;
import com.swampass.nauticalapp.model.ChatMessage;
import com.swampass.nauticalapp.model.User;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ChatConversationActivity extends AppCompatActivity {

    DatabaseReference myRef,myRef2,chatRef,userRef;
    ImageView attach_icon,send_icon,no_data_available_image;
    EditText message_area;
    TextView no_chat;
    public LinearLayoutManager mLinearLayoutManager;
    static String Sender_Name;
    public RecyclerView recyclerView;
    ChatItemAdapter adapter;
    ArrayList<ChatMessage> VAGINA;
    private FirebaseRecyclerAdapter<ChatMessage,ViewHolder> mUserMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_conversation);

        final String USER_ID = HomeActivity.LoggedIn_User_Email.replace("@","").replace(".","");
        String fuckingretard = getIntent().getStringExtra("descripion").replace("@","").replace(".","");
        myRef = FirebaseDatabase.getInstance().getReference().child("Chat").child(USER_ID).child(fuckingretard);
        myRef.keepSynced(true);
        //Log.d("LOGGED", "myRef : " + myRef);



        myRef2 = FirebaseDatabase.getInstance().getReference().child("Chat").child(getIntent().getStringExtra("descripion").replace("@","").replace(".","")).child(USER_ID);
        myRef2.keepSynced(true);

        chatRef = FirebaseDatabase.getInstance().getReference().child("Chat");
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        //Log.d("LOGGED", "myRef2 : " + myRef2);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getIntent().getStringExtra("name") + "</font>");
        }

        Sender_Name = getIntent().getStringExtra("name");

        attach_icon = (ImageView)findViewById(R.id.attachButton);
        send_icon = (ImageView)findViewById(R.id.sendButton);
        no_data_available_image = (ImageView)findViewById(R.id.no_data_available_image);
        message_area = (EditText)findViewById(R.id.messageArea);
        no_chat = (TextView)findViewById(R.id.no_chat_text);

        VAGINA = new ArrayList<>();
        adapter = new ChatItemAdapter(VAGINA,this,myRef2);

        recyclerView = (RecyclerView)findViewById(R.id.chat_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(ChatConversationActivity.this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        mLinearLayoutManager.setStackFromEnd(true);
        recyclerView.setAdapter(adapter);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        send_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = message_area.getText().toString().trim();

                if(!messageText.equals("")){

                    //Date dick = DateFormat.getDateInstance().getCalendar().getTime();
                    ChatMessage msg = new ChatMessage(messageText, USER_ID);
                    final DatabaseReference pushRef = chatRef.push();
                    final String pushKey = pushRef.getKey();



                   // ArrayMap<String, String> map = new ArrayMap<>();
                  //  map.put("message", messageText);
                 //   map.put("sender", HomeActivity.LoggedIn_User_Email);
                    //myRef.push().setValue(map);
                   // myRef2.push().setValue(map);

                    HashMap<String, Object> messageItemMap = new HashMap<String, Object>();
                    HashMap<String,Object> messageObj = (HashMap<String, Object>) new ObjectMapper().convertValue(msg, Map.class);
                    messageItemMap.put(pushKey, messageObj);

                    chatRef.push().setValue(messageItemMap);

                    VAGINA.add(msg);

                    message_area.setText("");


                }

            }

        });


        mUserMsg = new FirebaseRecyclerAdapter<ChatMessage, ViewHolder>(ChatMessage.class,R.layout.chat_item,ViewHolder.class,chatRef) {
            @Override
            protected void populateViewHolder(ViewHolder view, ChatMessage message, int position) {
                LinearLayout messageLine = view.layout;
                TextView messgaeText = view.message;
                TextView senderText = view.sender;
                //TextView timeTextView = (TextView) view.findViewById(R.id.timeTextView);
                final ImageView leftImage = view.leftPic;
                final ImageView rightImage = view.rightPic;
                LinearLayout individMessageLayout = view.messageLayout;

                //display timestamp correclty
               /* String time = message.getTimestamp();
                if(time != null && time != "" ) {
                    String ampm = "A.M.";
                    String hours = time.substring(0, 2);
                    String minutes = time.substring(3, 5);
                    int numHours = Integer.parseInt(hours);
                    if(numHours == 12){ //if numhours is 12 then its pm
                        ampm = "P.M.";
                    }
                    if (numHours > 12) {
                        numHours -= 12;
                        ampm = "P.M.";
                    }
                    if(numHours == 0){
                        numHours = 12;
                    }
                    hours = Integer.toString(numHours);
                    time = hours + ":" + minutes + " " + ampm;
                }
                timeTextView.setText(time);*/

                //set message and sender text
                messgaeText.setText(message.getMessageText());
                senderText.setText(message.getUserID());
                //If you sent this message, right align
                String mSender = message.getUserID();
//les garcoin other discord
                if(mSender.equals(HomeActivity.LoggedIn_User_Email)){
                    //messgaeText.setGravity(Gravity.RIGHT);
                    //senderText.setGravity(Gravity.RIGHT);
                    messageLine.setGravity(Gravity.RIGHT);
                    leftImage.setVisibility(View.GONE);
                    rightImage.setVisibility(View.VISIBLE);

                    //profile image back to here
                  /*  userRef.child(mSender).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User userInfo = dataSnapshot.getValue(User.class);
                            try{
                                if(userInfo != null && userInfo.getProfilePicLocation() != null){
                                    StorageReference storageRef = FirebaseStorage.getInstance()
                                            .getReference().child(userInfo.getProfilePicLocation());
                                    Glide.with(view.getContext())
                                            .using(new FirebaseImageLoader())
                                            .load(storageRef)
                                            .bitmapTransform(new CropCircleTransformation(view.getContext()))
                                            .into(rightImage);
                                }
                            }catch (Exception e){
                                Log.e("ERR", e.toString());
                                ;                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });*/

                    individMessageLayout.setBackgroundResource(R.drawable.incoming_msg);
                    //messgaeText.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                    //       R.color.colorAccent, null));
                }else if(mSender.equals("System")){
                    messageLine.setGravity(Gravity.CENTER_HORIZONTAL);
                    leftImage.setVisibility(View.GONE);
                    rightImage.setVisibility(View.GONE);
                }else{
                    //messgaeText.setGravity(Gravity.LEFT);
                    //senderText.setGravity(Gravity.LEFT);
                    messageLine.setGravity(Gravity.LEFT);
                    leftImage.setVisibility(View.VISIBLE);
                    rightImage.setVisibility(View.GONE);
                    individMessageLayout.setBackgroundResource(R.drawable.round_corners);
                    //messgaeText.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                    //       R.color.colorPrimary, null));


                    //profile image back to here
                  /*  mUsersDatabaseReference.child(mSender).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User userInfo = dataSnapshot.getValue(User.class);
                            if(userInfo != null && userInfo.getProfilePicLocation() != null){
                                try{
                                    StorageReference storageRef = FirebaseStorage.getInstance()
                                            .getReference().child(userInfo.getProfilePicLocation());
                                    Glide.with(view.getContext())
                                            .using(new FirebaseImageLoader())
                                            .load(storageRef)
                                            .bitmapTransform(new CropCircleTransformation(view.getContext()))
                                            .into(leftImage);
                                }catch(Exception e){
                                    Log.e("Err", e.toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });*/
                }

                //If this is multimedia display it
                /*final ImageView imageView = (ImageView) view.findViewById(R.id.imageMessage);
                final ImageButton activateVoiceMsg = (ImageButton) view.findViewById(R.id.voiceMessageButton);
                if(message.getMultimedia()){
                    if(message.getContentType().equals("IMAGE")) {
                        StorageReference storageRef = FirebaseStorage.getInstance()
                                .getReference().child(message.getContentLocation());
                        imageView.setVisibility(View.VISIBLE);
                        activateVoiceMsg.setVisibility(View.GONE);
                        activateVoiceMsg.setImageDrawable(null);
                        //storageRef.getDownloadUrl().addOnCompleteListener(new O)
                        Glide.with(view.getContext())
                                .using(new FirebaseImageLoader())
                                .load(storageRef)
                                .into(imageView);
                    }
                    if(message.getContentType().equals("VOICE")) {
                        //show play button
                        activateVoiceMsg.setVisibility(View.VISIBLE);
                        //hide imageview
                        imageView.setVisibility(View.GONE);
                        imageView.setImageDrawable(null);
                        //line below will reduce padding further on play audio image if necessary
                        //individMessageLayout.setPadding(10,0,0,10);
                        activateVoiceMsg.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(message.getContentLocation());
                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        playSound(uri);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors
                                    }
                                });

                            }
                        });
                    }
                }else{
                    activateVoiceMsg.setVisibility(View.GONE);
                    activateVoiceMsg.setImageDrawable(null);
                    imageView.setVisibility(View.GONE);
                    imageView.setImageDrawable(null);
                }*/
            }
        };
        recyclerView.setAdapter(mUserMsg);
    }




    public static class ViewHolder extends RecyclerView.ViewHolder  {
        private final TextView message, sender;
        private final ImageView leftPic,rightPic;
        private final LinearLayout.LayoutParams params, text_params;
        LinearLayout layout,messageLayout;


        public ViewHolder(final View itemView) {
            super(itemView);

            message = (TextView) itemView.findViewById(R.id.messageTextView);
            sender = (TextView) itemView.findViewById(R.id.senderTextView);
            //date = (TextView) itemView.findViewById(R.id.date_txt);
            text_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);;
            layout = (LinearLayout) itemView.findViewById(R.id.messageLine);
            leftPic = (ImageView) itemView.findViewById(R.id.leftMessagePic);
            rightPic = (ImageView) itemView.findViewById(R.id.rightMessagePic);
            messageLayout = (LinearLayout)itemView.findViewById(R.id.individMessageLayout);
        }

    }

}

