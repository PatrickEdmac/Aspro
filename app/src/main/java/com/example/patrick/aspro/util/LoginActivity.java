package com.example.patrick.aspro.util;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.patrick.aspro.MainActivity;
import com.example.patrick.aspro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText email;
    private EditText password;
    private Button bt_login;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.et_loginActivity_email);
        password = (EditText) findViewById(R.id.et_loginActivity_password);
        bt_login = (Button) findViewById(R.id.bt_loginActivity_login);

        bt_login.setOnClickListener(this);

        firebaseAuth = FirebaseAuthConfig.getFirebaseAuth();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Login");
        setSupportActionBar(toolbar);


    }


    @Override
    public void onClick(View v) {

        if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
            String email_txt = email.getText().toString();
            String password_txt = password.getText().toString();

            firebaseAuth.signInWithEmailAndPassword(email_txt,password_txt)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                firebaseUser = firebaseAuth.getCurrentUser();
                                if(firebaseUser.isEmailVerified()){
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                }
                               else{
                                    showEmailVerifiedDialog();
                                }


                            }
                            else{
                                try{
                                    throw task.getException();
                                }
                                catch (Exception e) {
                                    Toast.makeText(LoginActivity.this, "Usuário incorreto ou senha incorreta", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });
        }
        else{
            if(email.getText().toString().isEmpty()){
                Toast.makeText(this, "Preencha o campo 'email'", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Preencha o campo 'senha'", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showEmailVerifiedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("usuário não verificado");
        builder.setMessage("O cadastrado não foi confirmado, acesse sua caixa de entrada e clique no link de confirmação");
        builder.setCancelable(false);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("Enviar novamente", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendEmailConfirmation();
                dialog.dismiss();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void sendEmailConfirmation() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "email de confirmação enviado ", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(LoginActivity.this, "Erro ao enviar email de verificação", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
