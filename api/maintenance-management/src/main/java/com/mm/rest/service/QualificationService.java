/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.service;

import com.mm.rest.db.qualification.QualificationDatabase;
import com.mm.rest.models.equipment.QualificationModel;
import javax.ws.rs.core.Response;

/**
 *
 * @author burku
 */
public class QualificationService {
    private QualificationDatabase QDB = new QualificationDatabase();
    
    public Response addQualification(QualificationModel qualification) {
        
        int temp = QDB.addQualificationToDB(qualification);
        
        if(temp!=-1){
            return Response.status(Response.Status.OK).entity("Qualification added!").build();
        } else {
            return Response.status(Response.Status.NOT_MODIFIED).entity("ERROR!").build();
        }
    }
}
