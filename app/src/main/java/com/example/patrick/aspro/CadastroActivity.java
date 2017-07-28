package com.example.patrick.aspro;

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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patrick.aspro.models.User_pro;
import com.example.patrick.aspro.models.User_student;
import com.example.patrick.aspro.models.Usuario;
import com.example.patrick.aspro.util.FirebaseAuthConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.patrick.aspro.R.drawable.user;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirm_Password;
    private RadioGroup rg_occupation;
    private String occupation;
    private Button register;
    private Usuario user;


    private ArrayList<String> error_list;

    private Map<String,String> register_data;
    //private String[] register_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        firstName = (EditText) findViewById(R.id.et_signup_firstName);
        lastName = (EditText) findViewById(R.id.et_signup_lastName);
        email = (EditText) findViewById(R.id.et_signup_email);
        password = (EditText) findViewById(R.id.et_signup_password);
        confirm_Password = (EditText) findViewById(R.id.et_signup_confirmPassword);
        register = (Button) findViewById(R.id.bt_signup_register);
        rg_occupation = (RadioGroup) findViewById(R.id.rg_signup_occupation);


        toolbar.setTitle("Cadastro");
        setSupportActionBar(toolbar);

        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_signup_register:

            if(emptyFieldVerifier()){
                if(confirmPassword()){
                    createUser();
                    registerUser();

                    //Toast.makeText(getApplicationContext(),"todos campos preenchidos",Toast.LENGTH_LONG).show();
                }
                else{
                    showConfirmPasswordDialog();
                }

                //databaseRegister();
            }
            else {
                showEmptyFieldDialog(error_list);
            }
            break;
            case R.id.ib_signup_photo:
                break;
        }
    }
    public Boolean emptyFieldVerifier(){
        register_data = new HashMap<String,String>();
        register_data.put(getString(R.string.firstName),firstName.getText().toString());
        register_data.put(getString(R.string.lastName),lastName.getText().toString());
        register_data.put(getString(R.string.email),email.getText().toString());
        register_data.put(getString(R.string.password),password.getText().toString());
        register_data.put(getString(R.string.confirmPassword),confirm_Password.getText().toString());


        error_list = new ArrayList<String>();

        Boolean validate = true;


        for(Map.Entry<String, String> entry : register_data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(value.isEmpty()){
                error_list.add(key);
                validate = false;

            }

        }
        return validate;
    }
    public void showEmptyFieldDialog(ArrayList<String> error_list){
        TextView errors = new TextView(this);

        for(String erro : error_list){
            errors.append(erro+"\n");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(CadastroActivity.this);
        builder.setCancelable(true);
        builder.setView(errors);
        builder.setMessage(getString(R.string.fieldEmptyMsg));
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public Boolean confirmPassword(){
        return password.getText().toString().equals(confirm_Password.getText().toString());
    }
    public void showConfirmPasswordDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CadastroActivity.this);
        builder.setCancelable(true);
        builder.setMessage(getString(R.string.confirmPasswordMsg));
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void createUser(){

        occupation = getString(R.string.student_aspirant);
        user = new Usuario();
        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        user.setOccupation(occupation);

    }
    public void registerUser(){
        final FirebaseAuth firebaseAuth = FirebaseAuthConfig.getFirebaseAuth();
            firebaseAuth.createUserWithEmailAndPassword(
                    user.getEmail(),
                    user.getPassword()
            ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        final FirebaseAuth firebasAuth = FirebaseAuthConfig.getFirebaseAuth();
                        FirebaseUser firebaseUser = task.getResult().getUser();
                        user.setUid(firebaseUser.getUid());
                        user.saveUserInDatabase();

                        sendEmailConfirmation();
                    }
                    else{
                        try{
                            throw task.getException();
                        }
                        catch (FirebaseAuthUserCollisionException e ){
                            Toast.makeText(CadastroActivity.this, "O usuário já existe", Toast.LENGTH_SHORT).show();
                        }
                        catch(FirebaseAuthWeakPasswordException e){
                            Toast.makeText(CadastroActivity.this, "A senha deve possuir pelo menos 6 caracteres", Toast.LENGTH_SHORT).show();
                        }
                        catch(FirebaseAuthInvalidCredentialsException e){
                            Toast.makeText(CadastroActivity.this, "Endereço de email inválido", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e) {
                            String error = "erro ao cadastrar: "+e.getMessage();
                            Toast.makeText(CadastroActivity.this, error, Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });

    }


    private void sendEmailConfirmation(){
        final FirebaseAuth firebaseAuth = FirebaseAuthConfig.getFirebaseAuth();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    showEmailConfirmationDialog();

                }
                else{
                    Toast.makeText(CadastroActivity.this, "Erro ao enviar email de verificação", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showEmailConfirmationDialog() {
        final FirebaseAuth firebaseAuth = FirebaseAuthConfig.getFirebaseAuth();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        AlertDialog.Builder builder = new AlertDialog.Builder(CadastroActivity.this);
        builder.setCancelable(false);
        builder.setTitle("Cadastro efetuado com sucesso");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                firebaseAuth.signOut();
                startActivity(new Intent(CadastroActivity.this,InitActivity.class));
                finish();
            }
        });
        builder.setMessage("Um email de confirmação foi enviado para "+user.getEmail());
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
