package com.example.patrick.aspro.models;

import android.widget.Toast;

import com.example.patrick.aspro.util.FirebaseAuthConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Patrick on 24/07/2017.
 */

public class User_pro {

    private String Uid;
    private String name;
    private String desc;
    private String profession;
    private String wage;
    private String professionPos;
    private String email;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
    }

    public String getProfessionPos() {
        return professionPos;
    }

    public void setProfessionPos(String professionPos) {
        this.professionPos = professionPos;
    }

    public void saveUserInDatabase(){
                DatabaseReference databaseReference = FirebaseAuthConfig.getFirebase();
                databaseReference.child("Profissionais").child(getUid()).setValue(this);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User_pro(){

    }

}
