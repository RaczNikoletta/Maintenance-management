/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.db.subcategory;

import com.mm.rest.db.category.CategoryDatabase;
import com.mm.rest.db.DbConnection;
import com.mm.rest.models.equipment.SubCategoryModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author burkus
 */
public class SubCategoryDatabase {
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
            Logger.getLogger(CategoryDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
}
