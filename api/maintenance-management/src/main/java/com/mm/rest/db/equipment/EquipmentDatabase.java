/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.db.equipment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mm.rest.db.DbConnection;
import com.mm.rest.db.category.CategoryDatabase;
import com.mm.rest.exceptions.DatabaseException;
import com.mm.rest.models.equipment.EquipmentModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
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
    
    public ArrayNode getEquipments() throws DatabaseException {
        Connection con = DbConnection.getConnection();
        if(con==null) throw new DatabaseException("Not connected to db");
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM eszkozok");
            ResultSet rs = pstmt.executeQuery();

            ArrayNode arrayNode = mapper.createArrayNode();

            while (rs.next()) {
                ObjectNode row = mapper.createObjectNode();
                row.put("equipmentId", rs.getInt(1));
                row.put("subCategoryId", rs.getInt(2));
                row.put("equipmentName", rs.getString(3));
                row.put("site", rs.getString(4));
                row.put("description", rs.getString(5));
                row.put("error", rs.getBoolean(6));
                row.put("order", rs.getString(7));
                row.put("nextRepair", rs.getTimestamp(8).toString());
                arrayNode.add(row);
                
            }
            con.close();
            return arrayNode;
        } catch (SQLException ex) {
            Logger.getLogger(EquipmentDatabase.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseException("Not connected to db");
        }
    }
}