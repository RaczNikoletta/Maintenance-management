/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.service;

import com.mm.rest.db.equipment.EquipmentDatabase;
import com.mm.rest.models.equipment.EquipmentModel;
import javax.ws.rs.core.Response;

/**
 *
 * @author burkus
 */
public class EquipmentService {
    
    private EquipmentDatabase EDB = new EquipmentDatabase();
    
    public Response addEquipment(EquipmentModel equipment) {
        
        int temp = EDB.addEquipmentToDB(equipment);
        
        if(temp!=-1){
            return Response.status(Response.Status.OK).entity("Equipment added!").build();
        } else {
            return Response.status(Response.Status.NOT_MODIFIED).entity("ERROR!").build();
        }
    }
}
