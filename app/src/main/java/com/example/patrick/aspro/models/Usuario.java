package com.example.patrick.aspro.models;

import com.example.patrick.aspro.util.FirebaseAuthConfig;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Patrick on 21/07/2017.
 */

public class Usuario {
    private String Uid;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String occupation;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public void saveUserInDatabase(){
        DatabaseReference databaseReference = FirebaseAuthConfig.getFirebase();
        databaseReference.child("Usuários").child(getUid()).setValue(this);

    }
    public Usuario(){

    }
}
