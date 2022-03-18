package com.example.maintenance_manager_android;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    public static final String USER_TOKEN = "user_token";
    private Context context;
    private SharedPreferences prefs;


    public void setShared(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE);


    }

    public void saveAuthToken(String token){
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString(USER_TOKEN,token);
        ed.apply();
    }

    public String fetchAuthToken(){
        return prefs.getString(USER_TOKEN, null);
    }

}
