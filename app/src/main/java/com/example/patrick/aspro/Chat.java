package com.example.patrick.aspro;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.patrick.aspro.models.ChatMessage;
import com.example.patrick.aspro.models.Contact;
import com.example.patrick.aspro.util.FirebaseAuthConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chat extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageButton bt_send;
    private EditText msgBox;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference database;
    private ArrayList<String> messages;
    private ArrayAdapter adapter;
    private ListView msgList;
    private String senderID;
    private String addresseeID;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar = (Toolbar) findViewById(R.id.tb_chat);
        toolbar.setNavigationIcon(R.drawable.ic_menu_send);
        final Bundle extras = getIntent().getExtras();
        if(extras != null){
            toolbar.setTitle(extras.getString("name"));
        }
        setSupportActionBar(toolbar);

        bt_send = (ImageButton) findViewById(R.id.bt_chat_send);
        msgBox = (EditText) findViewById(R.id.et_chat_msg);
        msgList = (ListView)  findViewById(R.id.lv_chat);

        firebaseAuth = FirebaseAuthConfig.getFirebaseAuth();
        user = firebaseAuth.getCurrentUser();

        senderID = user.getUid();
        addresseeID = extras.getString("uid");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messages.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()
                     ) {
                    ChatMessage chatMessage = data.getValue(ChatMessage.class);
                    messages.add(chatMessage.getMsg());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        bt_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String msg = msgBox.getText().toString();
                if(!msg.isEmpty()){
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setuID(senderID);
                    chatMessage.setMsg(msg);
                    saveMessage(senderID,addresseeID,chatMessage);
                    msgBox.setText("");




                }
            }
        });

        messages = new ArrayList<>();
        adapter = new ArrayAdapter(Chat.this,android.R.layout.simple_list_item_1,messages);
        msgList.setAdapter(adapter);

        database = FirebaseAuthConfig.getFirebase();

        database.child("Messages").child(senderID).child(addresseeID).addValueEventListener(valueEventListener);

        Contact contact_addressee = new Contact();
        contact_addressee.setId(addresseeID);
        contact_addressee.setName(extras.getString("name"));
        contact_addressee.setEmail(extras.getString("email"));
        database.child("contatos").child(senderID).child(addresseeID).setValue(contact_addressee);

        Contact contact_sender = new Contact();
        contact_sender.setId(addresseeID);
        contact_sender.setName(extras.getString("name"));
        contact_sender.setEmail(extras.getString("email"));
        database.child("contatos").child(addresseeID).child(senderID).setValue(contact_sender);



    }
    private void saveMessage(String senderID, String addresseeID, ChatMessage message){

        try{
            database = FirebaseAuthConfig.getFirebase();
            database.child("Messages").child(senderID).child(addresseeID).push().setValue(message);
            if(!senderID.equals(addresseeID) ){
                database.child("Messages").child(addresseeID).child(senderID).push().setValue(message);

            }



        }
        catch(Exception e){
            Toast.makeText(this, "erro ao gravar msg: ", Toast.LENGTH_SHORT).show();
        }

    }

}
