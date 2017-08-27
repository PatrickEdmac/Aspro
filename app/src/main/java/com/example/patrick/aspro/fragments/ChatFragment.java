package com.example.patrick.aspro.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.patrick.aspro.ArrayAdapterProList;
import com.example.patrick.aspro.Chat;
import com.example.patrick.aspro.ProfileProActivity;
import com.example.patrick.aspro.R;
import com.example.patrick.aspro.models.User_pro;
import com.example.patrick.aspro.util.FirebaseAuthConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private ListView listView;
    private ArrayList<String> list ;
    private DatabaseReference databaseReference;



    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_conversas, container, false);

        listView = (ListView) v.findViewById(R.id.lv_conversa);
       // list.add("a");

        databaseReference = FirebaseAuthConfig.getFirebase();
        FirebaseAuth firebaseAuth = FirebaseAuthConfig.getFirebaseAuth();
        FirebaseUser user = firebaseAuth.getCurrentUser();


        databaseReference.child("Messages").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<String>();

                String s ="";
                for (DataSnapshot dataS: dataSnapshot.getChildren()
                     ) {
                     s = dataS.getKey();

                    list.add(s);

                }

            searchContact();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }
    private void searchContact(){
        databaseReference.child("Profissionais").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<User_pro> element = new ArrayList<User_pro>();

                for (DataSnapshot dataS: dataSnapshot.getChildren()
                     ) {
                    //Toast.makeText(getActivity(), list.get(0), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i<list.size();i++){
                        if(dataS.getKey().equals(list.get(i))){
                            User_pro user_pro = dataS.getValue(User_pro.class);
                            element.add(user_pro);
                        }
                    }


                }
                ArrayAdapter adapter = new ArrayAdapterProList(getActivity(),element);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        User_pro user_pro = element.get(position);
                        Intent intent = new Intent(getContext(), Chat.class);

                        intent.putExtra("name",user_pro.getName());

                        intent.putExtra("uid",user_pro.getUid());
                        

                        startActivity(intent);

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
