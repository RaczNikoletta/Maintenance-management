package com.example.maintenance_manager_android.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.sql.Timestamp;


public class TaskModel {
    @SerializedName("feladat_id")
    int feladat_id;
    @SerializedName("eszoz_id")
    int eszoz_id;
    @SerializedName("szakember_id")
    int szakember_id;
    @SerializedName("allapot")
    String allapot;
    @SerializedName("sulyossag")
    String sulyossag;
    @SerializedName("hiba_leiras")
    String hiba_leiras;
    @SerializedName("elutasitas_indok")
    String elutasitas_indok;
    @SerializedName("elkezdve")
    Date elkezdve;

    public TaskModel(int feladat_id,int eszoz_id,int szakember_id, String allapot,String sulyossag,
                     String hiba_leiras, String elutasitas_indok, Date elkezdve, Date befejezve){
        this.feladat_id = feladat_id;
        this.eszoz_id = eszoz_id;
        this.szakember_id = szakember_id;
        this.allapot = allapot;
        this.sulyossag = sulyossag;
        this.hiba_leiras = hiba_leiras;
        this.elutasitas_indok = elutasitas_indok;
        this.elkezdve = elkezdve;
        this.befejezve = befejezve;
    }
    public int getFeladat_id() {
        return feladat_id;
    }

    public void setFeladat_id(int feladat_id) {
        this.feladat_id = feladat_id;
    }

    public int getEszoz_id() {
        return eszoz_id;
    }

    public void setEszoz_id(int eszoz_id) {
        this.eszoz_id = eszoz_id;
    }

    public int getSzakember_id() {
        return szakember_id;
    }

    public void setSzakember_id(int szakember_id) {
        this.szakember_id = szakember_id;
    }

    public String getAllapot() {
        return allapot;
    }

    public void setAllapot(String allapot) {
        this.allapot = allapot;
    }

    public String getSulyossag() {
        return sulyossag;
    }

    public void setSulyossag(String sulyossag) {
        this.sulyossag = sulyossag;
    }

    public String getHiba_leiras() {
        return hiba_leiras;
    }

    public void setHiba_leiras(String hiba_leiras) {
        this.hiba_leiras = hiba_leiras;
    }

    public String getElutasitas_indok() {
        return elutasitas_indok;
    }

    public void setElutasitas_indok(String elutasitas_indok) {
        this.elutasitas_indok = elutasitas_indok;
    }

    public Date getElkezdve() {
        return elkezdve;
    }

    public void setElkezdve(Date elkezdve) {
        this.elkezdve = elkezdve;
    }

    public Date getBefejezve() {
        return befejezve;
    }

    public void setBefejezve(Date befejezve) {
        this.befejezve = befejezve;
    }

    @SerializedName("befejezve")
    Date befejezve;
}
