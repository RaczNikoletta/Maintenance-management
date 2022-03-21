/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.service;

import com.mm.rest.db.equipment.SubCategoryDatabase;
import com.mm.rest.models.equipment.SubCategoryModel;
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
}
