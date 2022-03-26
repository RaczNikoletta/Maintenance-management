/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.db.auth;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mm.rest.db.DbConnection;
import com.mm.rest.db.category.CategoryDatabase;
import com.mm.rest.db.subcategory.SubCategoryDatabase;
import com.mm.rest.exceptions.DatabaseException;
import com.mm.rest.models.database.Szakember;
import com.mm.rest.models.equipment.CategoryModel;
import com.mm.rest.models.equipment.SubCategoryModel;
import com.mm.rest.service.AuthService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author david
 */
public class AuthDatabase {
    public Szakember getUserFromDB(String username) throws DatabaseException {        
        Connection con = DbConnection.getConnection();
        if(con == null) throw new DatabaseException("Database connection failed in AuthDatabase.getUserFromDB");
        
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT *,Count(*) FROM szakember WHERE felhasznalonev LIKE ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
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
            if(count == 1 ) return sz;
            if(count == 0) throw new DatabaseException("No such user found: " + username);
            throw new DatabaseException("Multiple users with name: " + username);
        } catch (SQLException ex1) {
            Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex1);
            throw new DatabaseException("SQL exception in AuthDatabase.getUserFromDB");
        }
    }
    
    public boolean addUserToDB(String fnev, String nev, String hashedPw, String szerep) throws DatabaseException {        
        Connection con = DbConnection.getConnection();
        if(con == null) throw new DatabaseException("Database connection failed in AuthDatabase.addUserToDB");
        
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO `szakember` (felhasznalonev,nev,jelszo,szerep,munkaido,kepesites_id) VALUE (?,?,?,?,?,?)");
            ps.setString(1, fnev);
            ps.setString(2, nev);
            ps.setString(3, hashedPw);
            ps.setString(4, szerep);
            ps.setInt(5, 8);
            ps.setInt(6, 1);
            
            int temp = ps.executeUpdate();
            
            con.close();
            if(temp == 1 ) return true;
            if(temp == 0) throw new DatabaseException("Could't insert user: " + fnev);
            throw new DatabaseException("AtuhDatabase.addUserToDB, shouldnt have reached here");
        } catch (SQLException ex1) {
            Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex1);
            throw new DatabaseException("SQL exception in AuthDatabase.getUserFromDB");
        }
    }
    
    public boolean checkIfUserExistsInDB(String username) throws DatabaseException {        
        Connection con = DbConnection.getConnection();
        if(con == null) throw new DatabaseException("Database connection failed in AuthDatabase.checkIfUserExistsInDB");
        
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT Count(*) FROM szakember WHERE felhasznalonev LIKE ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }
            
            con.close();
            
            return count != 0;
        } catch (SQLException ex1) {
            Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex1);
            throw new DatabaseException("SQL exception in AuthDatabase.getUserFromDB");
        }
    }
}
