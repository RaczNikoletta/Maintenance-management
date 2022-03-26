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
public class QualificationModel {
    int qualificationID;
    String qualificationName;
     
    public QualificationModel(){
    }
    
    public QualificationModel(int qualificationID, String qualificationName){
        this.qualificationID = qualificationID;
        this.qualificationName = qualificationName;
    }

    public int getQualificationID() {
        return qualificationID;
    }

    public void setQualificationID(int qualificationID) {
        this.qualificationID = qualificationID;
    }

    public String getQualificationName() {
        return qualificationName;
    }

    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName;
    }
    
    
}
