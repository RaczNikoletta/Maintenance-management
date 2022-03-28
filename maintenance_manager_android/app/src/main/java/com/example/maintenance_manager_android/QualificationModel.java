package com.example.maintenance_manager_android;

import com.google.gson.annotations.SerializedName;

public class QualificationModel {

    @SerializedName("qualificationID")
    int qId;

    public int getqId() {
        return qId;
    }

    public void setqId(int qId) {
        this.qId = qId;
    }

    public String getqName() {
        return qName;
    }

    public void setqName(String qName) {
        this.qName = qName;
    }

    @SerializedName("qualificationName")
    String qName;
}
