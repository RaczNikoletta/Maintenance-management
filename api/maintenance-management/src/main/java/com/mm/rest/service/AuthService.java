/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mm.rest.UserContent;
import com.mm.rest.db.DbConnection;
import com.mm.rest.models.TestModel;
import com.mm.rest.models.database.Szakember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import javax.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author david
 */
public class AuthService {
    static EntityManagerFactory factory;
    static EntityManager entityManager;
    private static final ObjectMapper mapper = new ObjectMapper();
    
    //private static final Connection con = DbConnection.getConnection();
    
    public static Response login(String username, String password){
        Connection con = DbConnection.getConnection();
        if(con == null) return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Cannot reach Database").build();
        
        ObjectNode resp = mapper.createObjectNode();
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT *,Count(*) FROM szakember WHERE felhasznalonev LIKE ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            int count = -1;
            Szakember sz = new Szakember();
            while (rs.next()) {
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
            if(count != 1 ) return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No/Multiple users with such name").build();
            
            try {
                if(!BCrypt.checkpw(password, sz.getJelszo())) return Response.status(Response.Status.BAD_REQUEST).entity("Wrong password").build();
                
                Algorithm algorithm = Algorithm.HMAC256("secret");
                String token = JWT.create()
                    .withIssuer("auth0")
                    .withExpiresAt(toDate(LocalDateTime.now().plusMinutes(15)))
                    .withClaim("id", sz.getId())
                    .withClaim("role",sz.getSzerep())
                    .sign(algorithm);
                resp.put("JWT", token);
                
                return Response.status(Response.Status.OK).header(AUTHORIZATION, "Bearer " + token).entity(resp.toString()).build();
            } catch (JWTCreationException exception){
                con.close();
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex);
            try {
                con.close();
            } catch (SQLException ex1) {
                Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex1);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database Error").build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database query Error").build();
        }
    }
    
    public static boolean createAdmin(String fnev, String nev, String password, String szerep){
        Connection con = DbConnection.getConnection();
        if(con == null) return false;
        
        try {    
            String hashedPw = BCrypt.hashpw(password, BCrypt.gensalt());
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO `szakember` (felhasznalonev,nev,jelszo,szerep,munkaido,kepesites_id) VALUE (?,?,?,?,?,?)");
            pstmt.setString(1, fnev);
            pstmt.setString(2, nev);
            pstmt.setString(3, hashedPw);
            pstmt.setString(4, szerep);
            pstmt.setInt(5, 8);
            pstmt.setInt(6, 1);
            pstmt.executeUpdate();
            
            con.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    private static void begin(){
        factory = Persistence.createEntityManagerFactory("TestUnit");// TestUnit = a persistence.xml ben levo persistenceunit
        entityManager = factory.createEntityManager();
        
        entityManager.getTransaction().begin();
    }
    
    private static void close(){
        entityManager.getTransaction().commit();
        entityManager.close();
        factory.close();
    }
    
    private static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
