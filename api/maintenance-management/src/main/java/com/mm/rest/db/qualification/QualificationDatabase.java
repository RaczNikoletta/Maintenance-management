/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.db.qualification;

import com.mm.rest.db.DbConnection;
import com.mm.rest.models.equipment.QualificationModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author burkus
 */
public class QualificationDatabase {
        public int addQualificationToDB(QualificationModel qualification) {
        Connection con = DbConnection.getConnection();
        if(con==null) return -1;
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO `kepesites`(`kepesites_nev`) VALUES (?)");
            ps.setString(1, qualification.getQualificationName());
            int temp = ps.executeUpdate();
            con.close();
            return temp;
        } catch (SQLException ex) {
            Logger.getLogger(QualificationDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
}
