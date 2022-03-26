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
public class SubCategoryModel {
    private int subCategoryId;
    private int categoryId;
    private String subCategoryName;
    private int qualificationId;
    private int standardTime;
    private String order;
    
    public SubCategoryModel() {
    }

    public SubCategoryModel(int subCategoryId, String subCategoryName, int qualificationId, int standardTime, String order) {
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        //this.qualificationId = qualificationId;
        this.standardTime = standardTime;
        this.order = order;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public int getQualificationId() {
        return 0;
        //return qualificationId;
    }

    public void setQualificationId(int qualificationId) {
        //this.qualificationId = qualificationId;
    }

    public int getStandardTime() {
        return standardTime;
    }

    public void setStandardTime(int standardTime) {
        this.standardTime = standardTime;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
