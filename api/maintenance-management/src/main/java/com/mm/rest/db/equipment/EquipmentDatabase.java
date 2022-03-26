/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.db.equipment;

import com.mm.rest.db.DbConnection;
import com.mm.rest.models.equipment.EquipmentModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author burkus
 */
public class EquipmentDatabase {
    public int addEquipmentToDB(EquipmentModel equipment) {
        Timestamp timeStamp = Timestamp.from(ZonedDateTime.now().toInstant());
        Connection con = DbConnection.getConnection();
        if(con==null) return -1;
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO `eszkozok`(`eszkoznev`, `helyszin`, `leiras`, `hibas`, `utasitas`, `kov_javitas`) VALUES (?,?,?,?,?,?)");
            ps.setString(1, equipment.getEquipmentName());
            ps.setString(2, equipment.getSite());
            ps.setString(3, equipment.getDescription());
            ps.setBoolean(4, equipment.isError());
            ps.setString(5, equipment.getOrder());
            ps.setTimestamp(6, timeStamp);
            int temp = ps.executeUpdate();
            con.close();
            return temp;
        } catch (SQLException ex) {
            Logger.getLogger(EquipmentDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
}