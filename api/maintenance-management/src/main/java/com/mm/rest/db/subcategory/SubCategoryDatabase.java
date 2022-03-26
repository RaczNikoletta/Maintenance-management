/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.db.subcategory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mm.rest.db.category.CategoryDatabase;
import com.mm.rest.db.DbConnection;
import com.mm.rest.exceptions.DatabaseException;
import com.mm.rest.models.equipment.SubCategoryModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author burkus
 */
public class SubCategoryDatabase {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public int addSubCategoryToDB(SubCategoryModel subCategory) {
        Connection con = DbConnection.getConnection();
        if(con==null) return -1;
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO `alkategoria` (`kategoria_id`, `alkatnev`, `norma_ido`, `utasitas`) VALUES (?,?,?,?)");
            ps.setInt(1, subCategory.getSubCategoryId());
            ps.setString(2, subCategory.getSubCategoryName());
            //ps.setInt(3, subCategory.getQualificationId());
            ps.setInt(3, subCategory.getStandardTime());
            ps.setString(4, subCategory.getOrder());
            int temp = ps.executeUpdate();
            con.close();
            return temp;
        } catch (SQLException ex) {
            Logger.getLogger(SubCategoryDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    public ArrayNode getSubCategories() throws DatabaseException {
        Connection con = DbConnection.getConnection();
        if(con==null) throw new DatabaseException("Not connected to db");
        try {
            //PreparedStatement pstmt = con.prepareStatement("SELECT * FROM alkategoria ak JOIN kategoria k ON ak.kategoria_id = k.kategoria_id JOIN kepesites kep ON kep.kepesites_id = ak.kepesites_id");
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM alkategoria");
            ResultSet rs = pstmt.executeQuery();

            ArrayNode arrayNode = mapper.createArrayNode();

            while (rs.next()) {
                ObjectNode row = mapper.createObjectNode();
                /*row.put("subCategoryId", rs.getInt(1));
                row.put("categoryId", rs.getInt(2));
                row.put("categoryName", rs.getString(8));
                row.put("subCategoryName", rs.getString(3));
                row.put("qualificationId", rs.getInt(4));
                row.put("qualificationName", rs.getString(11));
                row.put("normTime", rs.getInt(5));
                row.put("instructions", rs.getString(6));
                row.put("repairInterval", rs.getInt(9));*/
                
                row.put("subCategoryId", rs.getInt(1));
                row.put("categoryId", rs.getInt(2));
                row.put("subCategoryName", rs.getString(3));
                row.put("qualificationId", rs.getInt(4));
                row.put("standardTime", rs.getInt(5));
                row.put("order", rs.getString(6));

                arrayNode.add(row);
            }
            con.close();
            return arrayNode;
        } catch (SQLException ex) {
            Logger.getLogger(SubCategoryDatabase.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseException("Not connected to db");
        }
    }
}
