package com.example.maintenance_manager_android.model;

public class ManageTaskModel {

    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    String severity;

    public ManageTaskModel(int id,String severity){
        this.id = id;
        this.severity = severity;
    }
}
