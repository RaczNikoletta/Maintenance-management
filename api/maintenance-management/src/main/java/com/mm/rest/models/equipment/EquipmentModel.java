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
public class EquipmentModel {
    private int equipmentId;
    private int subCategoryId;
    private String equipmentName;
    private String site;
    private String description;
    private boolean error;
    private String order;
    private String nextRepair;
    
    public EquipmentModel() {
    }

    public EquipmentModel(int equipmentId, int subCategoryId, String equipmentName,
            String site, String description, boolean error, String order, String nextRepair) {
        this.equipmentId = equipmentId;
        this.subCategoryId = subCategoryId;
        this.equipmentName = equipmentName;
        this.site = site;
        this.description = description;
        this.error = error;
        this.order = order;
        this.nextRepair = nextRepair;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getNextRepair() {
        return nextRepair;
    }

    public void setNextRepair(String nextRepair) {
        this.nextRepair = nextRepair;
    }
    
}
