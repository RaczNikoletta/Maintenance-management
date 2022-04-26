package com.example.maintenance_manager_android.model;

public class ListTasksModel {

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    private Integer taskId;
    private String equipment;
    private String state;
    private String severity;

    public ListTasksModel(){}

    public ListTasksModel(Integer taskId,String equipment,
                          String state, String severity) {

        this.equipment = equipment;
        this.state = state;
        this.severity = severity;
        this.taskId =  taskId;
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
