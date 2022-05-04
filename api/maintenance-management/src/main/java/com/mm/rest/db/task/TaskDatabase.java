/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.db.task;

import com.mm.rest.db.equipment.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mm.rest.db.DbConnection;
import com.mm.rest.db.category.CategoryDatabase;
import com.mm.rest.exceptions.DatabaseException;
import com.mm.rest.models.equipment.AddTaskModel;
import com.mm.rest.models.equipment.EquipmentModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author burkus
 */
public class TaskDatabase {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public int addTaskToDatabase(AddTaskModel task) {
        Connection con = DbConnection.getConnection();
        if(con==null) return -1;
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO `feladatok`(`eszkoz_id`,`allapot`,`sulyossag`,`hiba_leiras`,`felveve`) VALUES (?,'felveve',?,?,current_timestamp())");
            ps.setInt(1, task.getEquipmentId());
            ps.setString(2, task.getTaskSeverity());
            ps.setString(3, task.getErrorDescription());
            
            int temp = ps.executeUpdate();
            con.close();
            return temp;
        } catch (SQLException ex) {
            Logger.getLogger(TaskDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    public int addTasksToDatabase(List<AddTaskModel> tasks) {
        Connection con = DbConnection.getConnection();
        if(con==null) return -1;
        try {
            StringBuffer mySql = new StringBuffer("INSERT INTO `feladatok`(`eszkoz_id`,`allapot`,`sulyossag`,`hiba_leiras`,`felveve`) VALUES (?,'felveve',?,?,current_timestamp())");

            for (int i = 0; i < tasks.size() - 1; i++) {
                mySql.append(", (?,'felveve',?,?,current_timestamp())");
            }
            //myStatement = myConnection.prepareStatement(mySql.toString());
            
            PreparedStatement ps = con.prepareStatement(mySql.toString());
            for (int i = 0; i < tasks.size(); i++) {
                ps.setInt((i+1)*3-2, tasks.get(i).getEquipmentId());
                ps.setString((i+1)*3-1, tasks.get(i).getTaskSeverity());
                ps.setString((i+1)*3, tasks.get(i).getErrorDescription());
            }
            
            System.out.println(ps.toString());
            /*ps.setInt(1, task.getEquipmentId());
            ps.setString(2, task.getTaskSeverity());
            ps.setString(3, task.getErrorDescription());*/
            
            int temp = ps.executeUpdate();
            con.close();
            return temp;
        } catch (SQLException ex) {
            Logger.getLogger(TaskDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    public List<AddTaskModel> getIntervalRepairs() throws DatabaseException {
        Connection con = DbConnection.getConnection();
        if(con==null) throw new DatabaseException("Not connected to db");
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT e.*,k.*,ak.utasitas, f.* FROM `eszkozok` e "+
                                                            "JOIN `alkategoria` ak ON e.alkategoria_id = ak.alkategoria_id "+
                                                            "JOIN `kategoria` k ON ak.kategoria_id = k.kategoria_id "+
                                                            "LEFT JOIN (SELECT fs.feladat_id,fs.eszkoz_id FROM `feladatok` fs WHERE fs.allapot != 'elutasitott' AND fs.allapot != 'befejezve') as f ON f.eszkoz_id = e.eszkoz_id "+
                                                            "WHERE e.kov_javitas <= current_timestamp() "+
                                                            "ORDER BY e.kov_javitas ASC "
                                                        );
            
            
            
            ResultSet rs = pstmt.executeQuery();

            //ArrayNode arrayNode = mapper.createArrayNode();
            List<AddTaskModel> atl = new ArrayList<AddTaskModel>();
            while (rs.next()) {
                if(rs.getInt(12) == 0){
                    AddTaskModel atm = new AddTaskModel(rs.getInt(1),"alacsony",rs.getString(11));
                    atl.add(atm);
                }
            }
            con.close();
            return atl;
        } catch (SQLException ex) {
            Logger.getLogger(TaskDatabase.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseException("Not connected to db");
        }
    }
    
        public ArrayNode getTasks() throws DatabaseException {
        Connection con = DbConnection.getConnection();
        if(con==null) throw new DatabaseException("Not connected to db");
        try {
           // PreparedStatement pstmt = con.prepareStatement("SELECT * FROM feladatok");
            
             PreparedStatement pstmt = con.prepareStatement ("SELECT f.*, ak.utasitas FROM feladatok f "
                                                           + "JOIN eszkozok e ON f.eszkoz_id = e.eszkoz_id "
                                                           + "JOIN alkategoria ak ON ak.alkategoria_id = e.alkategoria_id ");
            ResultSet rs = pstmt.executeQuery();

            ArrayNode arrayNode = mapper.createArrayNode();

            while (rs.next()) {
                ObjectNode row = mapper.createObjectNode();
                row.put("feladat_id", rs.getInt(1));
                row.put("eszoz_id", rs.getInt(2));
                row.put("szakember_id", rs.getInt(3));
                row.put("allapot", rs.getString(4));
                row.put("sulyossag", rs.getString(5));
                row.put("hiba_leiras", rs.getString(6));
                row.put("elutasitas_indok", rs.getString(7));
                
                if(rs.getTimestamp(8) == null){
                    row.put("felveve", "");
                } else {
                    row.put("felveve", rs.getTimestamp(8).toString());
                }
                
                if(rs.getTimestamp(9) == null){
                    row.put("kiosztva", "0000-00-00 00:00:00");
                } else {
                    row.put("kiosztva", rs.getTimestamp(9).toString());
                }
                
                if(rs.getTimestamp(10) == null){
                    row.put("elkezdve", "0000-00-00 00:00:00");
                } else {
                    row.put("elkezdve", rs.getTimestamp(10).toString());
                }
                
                if(rs.getTimestamp(11) == null){
                    row.put("befejezve", "0000-00-00 00:00:00");
                } else {
                    row.put("befejezve", rs.getTimestamp(11).toString());
                }
                
                row.put("utasitas", rs.getString(12));
                
                arrayNode.add(row);               
            }
            con.close();
            return arrayNode;
        } catch (SQLException ex) {
            Logger.getLogger(TaskDatabase.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseException("Not connected to db");
        }
    }
        
    public boolean assignTask(int user_id, int taskId) throws DatabaseException{
        Connection con = DbConnection.getConnection();
        if(con==null) throw new DatabaseException("Not connected to db");
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE `feladatok` SET `szakember_id` = ? , `allapot` = 'kiosztva' , `kiosztva` = current_timestamp() WHERE `feladat_id` = ? ");
            ps.setInt(1, user_id);
            ps.setInt(2, taskId);
            int temp = ps.executeUpdate();
            
            con.close();
            
            if(temp == 1){
                return true;
            }else{
                throw new DatabaseException("Couldnt assign user: " + String.valueOf(user_id) +" to task: " + String.valueOf(taskId));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TaskDatabase.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }
    
    public int getTaskQualification(int taskId) throws DatabaseException{
        Connection con = DbConnection.getConnection();
        if(con==null) throw new DatabaseException("Not connected to db");
        try {
            PreparedStatement ps = con.prepareStatement("SELECT ak.kepesites_id,Count(*) FROM feladatok f "
                                                      + "JOIN eszkozok e ON f.eszkoz_id = e.eszkoz_id "
                                                      + "JOIN alkategoria ak ON ak.alkategoria_id = e.alkategoria_id "
                                                      + "WHERE f.feladat_id = ? ");
            ps.setInt(1, taskId);
            ResultSet rs = ps.executeQuery();         

            int count = 0;
            int qualificationId = 0;
            while (rs.next()) {
                count = rs.getInt(2);
                if(rs.getInt(2) == 1) {
                    count = rs.getInt(2); 
                    qualificationId = rs.getInt(1);
                }
            }
            con.close();
            
            if(count == 1){
                return qualificationId;
            }else{
                throw new DatabaseException("No such task");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TaskDatabase.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }
}