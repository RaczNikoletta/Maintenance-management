package com.example.maintenance_manager_android;

import com.google.gson.annotations.SerializedName;

public class createResponse {

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFnev() {
        return fnev;
    }

    public void setFnev(String fnev) {
        this.fnev = fnev;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSzerep() {
        return szerep;
    }

    public void setSzerep(String szerep) {
        this.szerep = szerep;
    }

    @SerializedName("success")
    boolean success;

    @SerializedName("fnev")
    String fnev;

    @SerializedName("nev")
    String nev;

    @SerializedName("password")
    String password;

    @SerializedName("szerep")
    String szerep;

}
