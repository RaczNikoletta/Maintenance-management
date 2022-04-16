/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.models.equipment;

import java.sql.Timestamp;

/**
 *
 * @author burkus
 */
public class AddTaskModel {
    private int equipmentId;
    private String taskSeverity;
    private String errorDescription;
    
    public AddTaskModel() {
    }

    public AddTaskModel(int equipmentId, String taskSeverity, String errorDescription) {
        this.equipmentId = equipmentId;
        this.taskSeverity = taskSeverity;
        this.errorDescription = errorDescription;
    }

    public String getTaskSeverity() {
        return taskSeverity;
    }

    public void setTaskSeverity(String taskSeverity) {
        this.taskSeverity = taskSeverity;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }



    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    
    
}
