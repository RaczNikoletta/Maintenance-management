/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mm.rest.db.category.CategoryDatabase;
import com.mm.rest.exceptions.DatabaseException;
import com.mm.rest.models.equipment.CategoryModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;

/**
 *
 * @author burkus
 */
public class CategoryService {
    
    private CategoryDatabase CDB = new CategoryDatabase();
    
    public Response addCategory(CategoryModel category) {
        
        int temp = CDB.addCategoryToDB(category);
        
        if(temp!=-1){
            return Response.status(Response.Status.OK).entity("Category added!").build();
        } else {
            return Response.status(Response.Status.NOT_MODIFIED).entity("ERROR!").build();
        }
    }
    
    public Response getCategories() {
        try {
            ArrayNode categories = CDB.getCategories();
            return Response.status(Response.Status.OK).entity(categories.toString()).build();
        } catch (DatabaseException ex) {
            Logger.getLogger(CategoryService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_MODIFIED).entity("ERROR!").build();
        }
    }
}
