package com.example.patrick.aspro.models;

/**
 * Created by Patrick on 16/08/2017.
 */

public class Contact {
    private String id;
    private String name;
    private String email;

    public Contact() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
