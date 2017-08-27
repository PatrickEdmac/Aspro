package com.example.patrick.aspro.models;

/**
 * Created by Patrick on 02/08/2017.
 */

public class ChatMessage {
    private String uID;
    private String msg;

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
