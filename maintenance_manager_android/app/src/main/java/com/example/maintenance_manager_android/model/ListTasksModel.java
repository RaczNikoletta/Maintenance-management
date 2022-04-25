package com.example.maintenance_manager_android.model;

public class ListTasksModel {
    private int taskId;
    private int equipmentId;
    private int qualificationID;
    private String state;
    private String severity;

    public ListTasksModel(){}

    public ListTasksModel(int taskId, int equipmentId, int qualificationID,
                          String state, String severity) {
        this.taskId = taskId;
        this.equipmentId = equipmentId;
        this.qualificationID = qualificationID;
        this.state = state;
        this.severity = severity;
    }

    public int getTaskId() {
        return taskId;
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }
    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getQualificationID() {
        return qualificationID;
    }
    public void setQualificationID(int qualificationID) {
        this.qualificationID = qualificationID;
    }

    public String getTaskState() {
        return state;
    }
    public void setTaskState(String state) {
        this.state = state;
    }

    public String getTaskSeverity() {
        return severity;
    }
    public void setTaskSeverity(String severity) {
        this.severity = severity;
    }
}
