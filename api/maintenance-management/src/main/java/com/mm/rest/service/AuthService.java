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
import com.mm.rest.db.auth.AuthDatabase;
import com.mm.rest.db.category.CategoryDatabase;
import com.mm.rest.exceptions.DatabaseException;
import com.mm.rest.exceptions.ServiceException;
import com.mm.rest.helper.Constants;
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
    
    private final AuthDatabase ADB = new AuthDatabase();
    
    public Response login(String username, String password) throws ServiceException{
        ObjectNode resp = mapper.createObjectNode();
        try {
            Szakember user = ADB.getUserFromDB(username);
            if(!BCrypt.checkpw(password, user.getJelszo())) throw new ServiceException("Wrong password","password");

            Algorithm algorithm = Algorithm.HMAC256(Constants.JWT_SECRET);
            String token = JWT.create()
                .withIssuer("auth0")
                .withExpiresAt(toDate(LocalDateTime.now().plusMinutes(15)))
                .withClaim("id", user.getId())
                .withClaim("role",user.getSzerep())
                .sign(algorithm);
            resp.put("JWT", token);

            return Response.status(Response.Status.OK).header(AUTHORIZATION, "Bearer " + token).entity(resp.toString()).build();
        } catch (JWTCreationException exception){
            throw new ServiceException(exception.getMessage(),"jwt");
        } catch (DatabaseException ex){
            throw new ServiceException(ex.getMessage(),"database");
        }
    }
    
    public Response createUser(String fnev, String nev, String password, String szerep) throws ServiceException{
        try {    
            if(ADB.checkIfUserExistsInDB(fnev)) throw new ServiceException("User already exists: " + fnev, "database");
            
            String hashedPw = BCrypt.hashpw(password, BCrypt.gensalt());
            if(ADB.addUserToDB(fnev, nev, hashedPw, szerep)){
                return Response.status(Response.Status.OK).entity("Successfully created user").build();
            }
            
            throw new ServiceException("Shouldnt reach here","database");
        } catch (DatabaseException ex) {
            Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage(),"database");
        }
    }
    
    private static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
