package com.example.maintenance_manager_android.model;

public class CategoryModel {
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
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

    int categoryID;
    String categoryName;
    int repairInterval;

}
