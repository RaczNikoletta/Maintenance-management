/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mm.rest.db.subcategory.SubCategoryDatabase;
import com.mm.rest.exceptions.DatabaseException;
import com.mm.rest.models.equipment.SubCategoryModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;

/**
 *
 * @author burkus
 */
public class SubCategoryService {
    
    private SubCategoryDatabase SCDB = new SubCategoryDatabase();
    
    public Response addSubCategory(SubCategoryModel subCategory) {
        
        int temp = SCDB.addSubCategoryToDB(subCategory);
        
        if(temp!=-1){
            return Response.status(Response.Status.OK).entity("Subcategory added!").build();
        } else {
            return Response.status(Response.Status.NOT_MODIFIED).entity("ERROR!").build();
        }
    }
    
    public Response getSubCategories() {
        try {
            ArrayNode subCategories = SCDB.getSubCategories();
            return Response.status(Response.Status.OK).entity(subCategories.toString()).build();
        } catch (DatabaseException ex) {
            Logger.getLogger(SubCategoryService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_MODIFIED).entity("ERROR!").build();
        }
    }
}
