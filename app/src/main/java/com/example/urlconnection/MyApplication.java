package com.example.urlconnection;

import android.app.Application;

public class MyApplication extends android.app.Application {

    private String apikey;
    private int userStatus=9; // 0=> New User,   1=> Exisiting User ,   2=> Password Mismatch

    public String getApikey() {
        return apikey;
    }
    public int getUserStatus() {
        return userStatus;
    }
    public void setApikey(String apikey) {
        this.apikey = apikey;
    }
    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }
}