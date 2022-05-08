package com.example.maintenance_manager_android.model;

import java.sql.Date;

public class ListAssignedTasksModel {


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    private Date date;
    private String location;
    private String severity;

    public ListAssignedTasksModel(){}

    public ListAssignedTasksModel(String location,
                          String severity, Date date) {

        this.location = location;
        this.severity = severity;
        this.date = date;
    }


    public ListAssignedTasksModel(String location) {
        this.location = location;
    }
}
