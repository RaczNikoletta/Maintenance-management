/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.db.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mm.rest.db.DbConnection;
import com.mm.rest.exceptions.DatabaseException;
import com.mm.rest.models.equipment.CategoryModel;
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
public class CategoryDatabase {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public int addCategoryToDB(CategoryModel category) {
        Connection con = DbConnection.getConnection();
        if(con==null) return -1;
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO `kategoria`(`kateg_nev`, `javitas_intervallum`) VALUES (?,?)");
            ps.setString(1, category.getCategoryName());
            ps.setInt(2, category.getRepairInterval());
            int temp = ps.executeUpdate();
            con.close();
            return temp;
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
        public ArrayNode getCategories() throws DatabaseException {
        Connection con = DbConnection.getConnection();
        if(con==null) throw new DatabaseException("Not connected to db");
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM kategoria");
            ResultSet rs = pstmt.executeQuery();

            ArrayNode arrayNode = mapper.createArrayNode();

            while (rs.next()) {
                ObjectNode row = mapper.createObjectNode();
                row.put("categoryID", rs.getInt(1));
                row.put("categoryName", rs.getString(2));
                row.put("repairInterval", rs.getInt(3));
                arrayNode.add(row);
            }
            con.close();
            return arrayNode;
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDatabase.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseException("Not connected to db");
        }
    }
}
