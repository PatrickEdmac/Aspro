package com.example.patrick.aspro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.patrick.aspro.util.FirebaseAuthConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView welcomeMsg;
    private Button logOff;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuthConfig.getFirebaseAuth();
        firebaseUser = firebaseAuth.getCurrentUser();

        welcomeMsg = (TextView) findViewById(R.id.tv_mainActivity_welcome);
        welcomeMsg.setText("Bem-vindo\n "+firebaseUser.getEmail());
        logOff = (Button) findViewById(R.id.bt_mainActivity_logOff);

        logOff.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this,InitActivity.class));
        finish();
    }
}
