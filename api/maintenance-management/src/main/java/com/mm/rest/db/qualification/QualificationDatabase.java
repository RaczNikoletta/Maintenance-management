/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.db.qualification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mm.rest.db.DbConnection;
import com.mm.rest.db.category.CategoryDatabase;
import com.mm.rest.exceptions.DatabaseException;
import com.mm.rest.models.database.Szakember;
import com.mm.rest.models.equipment.QualificationModel;
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
public class QualificationDatabase {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
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
        
    public ArrayNode getQualifications() throws DatabaseException {
        Connection con = DbConnection.getConnection();
        if(con==null) throw new DatabaseException("Not connected to db");
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM kepesites");
            ResultSet rs = pstmt.executeQuery();

            ArrayNode arrayNode = mapper.createArrayNode();

            while (rs.next()) {
                ObjectNode row = mapper.createObjectNode();
                row.put("qualificationID", rs.getInt(1));
                row.put("qualificationName", rs.getString(2)); 
                arrayNode.add(row);
            }
            con.close();
            return arrayNode;
        } catch (SQLException ex) {
            Logger.getLogger(QualificationDatabase.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseException("Not connected to db");
        }
    }
    
    public boolean checkIfQualified(int user_id, int qualificationId) throws DatabaseException {
        Connection con = DbConnection.getConnection();
        if(con==null) throw new DatabaseException("Not connected to db");
        try {            
            PreparedStatement ps = con.prepareStatement("SELECT *,Count(*) FROM szakember WHERE id = ?");
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();

            int count = 0;
            Szakember sz = new Szakember();
            while (rs.next()) {
                count = rs.getInt(8);
                if(rs.getInt(8) == 1) {
                    count = rs.getInt(8); 
                    sz.setId(rs.getInt(1));
                    sz.setFelhasznalonev(rs.getString(2));
                    sz.setNev(rs.getString(3));
                    sz.setSzerep(rs.getString(4));
                    sz.setJelszo(rs.getString(5));
                    sz.setMunkaido(rs.getInt(6));
                    sz.setKepesitesId(rs.getInt(7));
                }
            }
            con.close();
            
            if(count == 1){
                if(qualificationId == sz.getKepesitesId()){
                    return true;
                }else{
                    throw new DatabaseException("Invalid qualifications");
                }
            }else{
                throw new DatabaseException("No such user");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QualificationDatabase.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseException("Not connected to db");
        }
    }
}
