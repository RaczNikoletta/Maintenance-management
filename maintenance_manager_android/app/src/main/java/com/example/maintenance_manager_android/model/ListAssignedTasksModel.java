package com.example.maintenance_manager_android.model;

import android.widget.LinearLayout;

import java.sql.Date;

public class ListAssignedTasksModel {


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    private Date date;
    private String status;
    private String severity;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    private String order;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    private String location;
    private String errorDesc;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    private Date startTime;


    public ListAssignedTasksModel(){}

    public ListAssignedTasksModel(String location,
                          String severity, Date date,String loc,String errorDesc,Date startTime,String order) {

        this.status = location;
        this.severity = severity;
        this.date = date;
        this.location = loc;
        this.errorDesc = errorDesc;
        this.startTime = startTime;
        this.order = order;
    }


    public ListAssignedTasksModel(String location) {
        this.status = location;
    }
}
