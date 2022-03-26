/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.models.equipment;

/**
 *
 * @author burkus
 */
public class CategoryModel {
    private int categoryID;
    private String categoryName;
    private int repairInterval;
    
    public CategoryModel() {
    }

    public CategoryModel(String categoryName, int repairInterval) {
        this.categoryName = categoryName;
        this.repairInterval = repairInterval;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getRepairInterval() {
        return repairInterval;
    }

    public void setRepairInterval(int repairInterval) {
        this.repairInterval = repairInterval;
    }
}
