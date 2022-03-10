/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.db.equipment;

import com.mm.rest.db.DbConnection;
import com.mm.rest.models.equipment.CategoryModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author burkus
 */
public class EquipmentDatabase {
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
            Logger.getLogger(EquipmentDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
}
