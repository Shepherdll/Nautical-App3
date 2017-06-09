package com.swampass.nauticalapp;

import android.app.ProgressDialog;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.swampass.nauticalapp.model.ActiveChatConvo;
import com.swampass.nauticalapp.model.ChatItemAdapter;
import com.swampass.nauticalapp.model.ChatMessage;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ChatConversationActivity extends AppCompatActivity {

    DatabaseReference myRef,myRef2;
    ImageView attach_icon,send_icon,no_data_available_image;
    EditText message_area;
    TextView no_chat;
    public LinearLayoutManager mLinearLayoutManager;
    static String Sender_Name;
    public RecyclerView recyclerView;
    ChatItemAdapter adapter;
    ArrayList<ChatMessage> VAGINA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_conversation);

        String USER_ID = HomeActivity.LoggedIn_User_Email.replace("@","").replace(".","");
        String fuckingretard = getIntent().getStringExtra("descripion").replace("@","").replace(".","");
        myRef = FirebaseDatabase.getInstance().getReference().child("Chat").child(USER_ID).child(fuckingretard);
        myRef.keepSynced(true);
        //Log.d("LOGGED", "myRef : " + myRef);



        myRef2 = FirebaseDatabase.getInstance().getReference().child("Chat").child(getIntent().getStringExtra("descripion").replace("@","").replace(".","")).child(USER_ID);
        myRef2.keepSynced(true);
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
                    ArrayMap<String, String> map = new ArrayMap<>();
                    map.put("message", messageText);
                    map.put("sender", HomeActivity.LoggedIn_User_Email);
                    myRef.push().setValue(map);
                    myRef2.push().setValue(map);
                    Date dick = DateFormat.getDateInstance().getCalendar().getTime();
                    VAGINA.add(new ChatMessage(messageText, HomeActivity.LoggedIn_User_Email,dick) );

                    message_area.setText("");
                    recyclerView.postDelayed(new Runnable() {
                        @Override public void run()
                        {
                            recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount()-1);

                        }
                    }, 500);

                }
                adapter.notifyDataSetChanged();
            }

        });



    }
}
