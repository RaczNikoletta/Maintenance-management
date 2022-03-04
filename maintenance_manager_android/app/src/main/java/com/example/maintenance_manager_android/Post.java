package com.example.maintenance_manager_android;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Post {


    public String getKey() {
        return key;
    }




    public void setKey(String key) {
        this.key = key;
    }


    @SerializedName("example_key")
    private String key;


    @SerializedName("example_key2")
    private String key2;

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

}

