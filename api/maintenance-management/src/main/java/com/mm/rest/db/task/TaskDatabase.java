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
            PreparedStatement ps = con.prepareStatement("INSERT INTO `feladatok`(`eszkoz_id`,`allapot`,`sulyossag`,`hiba_leiras`) VALUES (?,'felveve',?,?)");
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
            StringBuffer mySql = new StringBuffer("INSERT INTO `feladatok`(`eszkoz_id`,`allapot`,`sulyossag`,`hiba_leiras`) VALUES (?,'felveve',?,?)");

            for (int i = 0; i < tasks.size() - 1; i++) {
                mySql.append(", (?,'felveve',?,?)");
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
            PreparedStatement pstmt = con.prepareStatement("SELECT e.*,k.*,ak.utasitas FROM `eszkozok` e "
                                                         + "JOIN `alkategoria` ak ON e.alkategoria_id = ak.alkategoria_id "
                                                         + "JOIN `kategoria` k ON ak.kategoria_id = k.kategoria_id "
                                                         + "WHERE e.kov_javitas <= current_timestamp() "
                                                         + "ORDER BY e.kov_javitas ASC "
                                                        );
            ResultSet rs = pstmt.executeQuery();

            //ArrayNode arrayNode = mapper.createArrayNode();
            List<AddTaskModel> atl = new ArrayList<AddTaskModel>();
            while (rs.next()) {
                AddTaskModel atm = new AddTaskModel(rs.getInt(1),"alacsony",rs.getString(11));
                atl.add(atm);
                /*ObjectNode row = mapper.createObjectNode();
                row.put("equipmentId", rs.getInt(1));
                row.put("subCategoryId", rs.getInt(2));
                row.put("categoryId", rs.getInt(8));
                row.put("equipmentName", rs.getString(3));
                row.put("site", rs.getString(4));
                row.put("description", rs.getString(5));
                row.put("error", rs.getBoolean(6));
                row.put("order", rs.getString(7));
                row.put("nextRepair", rs.getTimestamp(8).toString());
                arrayNode.add(row);*/
                
                
            }
            con.close();
            return atl;
        } catch (SQLException ex) {
            Logger.getLogger(TaskDatabase.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseException("Not connected to db");
        }
    }
}