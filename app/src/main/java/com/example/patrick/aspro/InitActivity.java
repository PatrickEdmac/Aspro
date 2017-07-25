package com.example.patrick.aspro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.patrick.aspro.util.FirebaseAuthConfig;
import com.example.patrick.aspro.util.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InitActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_registrar;
    private Button bt_login;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        firebaseAuth = FirebaseAuthConfig.getFirebaseAuth();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            if(firebaseUser.isEmailVerified()){
                startActivity(new Intent(InitActivity.this, MainActivity.class));
            }

        }

        bt_registrar = (Button) findViewById(R.id.bt_registrar);
        bt_login = (Button) findViewById(R.id.bt_login);

        bt_registrar.setOnClickListener(this);
        bt_login.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_registrar:

                Intent i = new Intent(InitActivity.this,CadastroActivity.class);
                startActivity(i);
                break;
            case R.id.bt_login:
                startActivity(new Intent(InitActivity.this, LoginActivity.class));
                break;
            // ...
        }
    }

}
