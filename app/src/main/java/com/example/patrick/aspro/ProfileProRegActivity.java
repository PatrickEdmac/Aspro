package com.example.patrick.aspro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.patrick.aspro.models.User_pro;
import com.example.patrick.aspro.util.FirebaseAuthConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ProfileProRegActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText name;
    private EditText email;
    private EditText desc;
    private Spinner spinner;
    private int spinner_item_pos;
    private Button bt_atualizar;
    private EditText wage;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    private User_pro user;
    private User_pro userInfo;

    private String accType;
    private String profession;
    private String professionPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pro_reg);

        name = (EditText) findViewById(R.id.et_profilePro_name);
        email = (EditText) findViewById(R.id.et_profilePro_email);
        desc = (EditText) findViewById(R.id.et_profilePro_desc);
        spinner = (Spinner) findViewById(R.id.spinner_profilePro_pro);
        bt_atualizar = (Button) findViewById(R.id.bt_perfilPro_update);
        wage = (EditText) findViewById(R.id.et_profilePro_wage);
        bt_atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        firebaseAuth = FirebaseAuthConfig.getFirebaseAuth();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseAuthConfig.getFirebase();
        databaseReference.child("Profissionais").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfo = dataSnapshot.getValue(User_pro.class);
                if(userInfo !=null){
                    fillInfo();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.profissoes,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }
    private void update(){
        if(firebaseUser != null){
            String userId = firebaseUser.getUid();
            createUser();
            user.setUid(firebaseUser.getUid());
            user.saveUserInDatabase();
            Toast.makeText(this, "Perfil profissional registrado com sucesso", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(this, "Usuário não indentificado", Toast.LENGTH_SHORT).show();
        }

    }
    private void createUser(){
        user = new User_pro();
        user.setName(name.getText().toString());
        user.setDesc(desc.getText().toString());
        user.setProfession(profession);
        user.setWage(wage.getText().toString());
        user.setProfessionPos(professionPos);

    }
    private void fillInfo(){
        name.setText(userInfo.getName());
        desc.setText(userInfo.getDesc());
        wage.setText(userInfo.getWage());
        spinner.setSelection(Integer.parseInt(userInfo.getProfessionPos()));
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        profession = parent.getItemAtPosition(pos).toString();
        professionPos = String.valueOf(pos);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
