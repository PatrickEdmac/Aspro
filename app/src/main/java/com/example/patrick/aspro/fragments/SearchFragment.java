package com.example.patrick.aspro.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.patrick.aspro.ArrayAdapterProList;
import com.example.patrick.aspro.ProListElement;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private ListView list;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

         list = (ListView) v.findViewById(R.id.lv_searchPro);


        DatabaseReference databaseReference = FirebaseAuthConfig.getFirebase();

        databaseReference.child("Profissionais").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<User_pro> element = new ArrayList<User_pro>();

                for (DataSnapshot user_proSnapshot: dataSnapshot.getChildren()) {

                    User_pro user_pro = user_proSnapshot.getValue(User_pro.class);

                    element.add(user_pro);

                }
                ArrayAdapter adapter = new ArrayAdapterProList(getActivity(),element);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        User_pro user_pro = element.get(position);
                        Intent intent = new Intent(getContext(), ProfileProActivity.class);

                        intent.putExtra("name",user_pro.getName());
                        intent.putExtra("profession",user_pro.getProfession());
                        intent.putExtra("desc",user_pro.getDesc());
                        intent.putExtra("uid",user_pro.getUid());
                        intent.putExtra("email",user_pro.getEmail());

                        startActivity(intent);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });



        return v;
    }
}
