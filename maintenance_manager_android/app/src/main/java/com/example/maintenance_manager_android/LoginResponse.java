package com.example.maintenance_manager_android;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @SerializedName("id")
    int id;

    @SerializedName("role")
    String role;

    public String getAut_token() {
        return aut_token;
    }

    public void setAut_token(String aut_token) {
        this.aut_token = aut_token;
    }

    @SerializedName("JWT")
    String aut_token;
}
