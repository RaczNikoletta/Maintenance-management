/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.resources;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mm.rest.Example;
import com.mm.rest.UserContent;
import com.mm.rest.db.DbConnection;
import com.mm.rest.exceptions.ServiceException;
import com.mm.rest.filter.JwtTokenNeeded;
import com.mm.rest.helper.JwtProperties;
import com.mm.rest.service.TestService;
import com.mm.rest.models.TestModel;
import com.mm.rest.models.authentication.LoginModel;
import com.mm.rest.service.AuthService;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

/**
 *
 * @author david
 */
// http://localhost:8080/api/auth
@Path("/auth")
public class AuthResource {
    private static final ObjectMapper mapper = new ObjectMapper();
    //private static final Connection con = DbConnection.getConnection();
    private static final AuthService as = new AuthService();
    
    // http://localhost:8080/api/auth/login
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(LoginModel login) {
        try{
            Response success = as.login(login.getUsername(),login.getPassword());

            return success;
            
        }catch(ServiceException ex){
            System.out.println(ex);
            switch(ex.getCode()){
                case "jwt":return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
                case "password":return Response.status(Response.Status.FORBIDDEN).entity(ex.getMessage()).build();
                case "database":return Response.status(Response.Status.FORBIDDEN).entity(ex.getMessage()).build();
                default:return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(ex.getCode()).build();
            }
        }
    }
    
    //Paraméterek: fnev, nev, password, szerep, kepesitesid
    // http://localhost:8080/api/auth/create
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@QueryParam("fnev") String fnev,@QueryParam("nev") String nev,
@QueryParam("password") String password,@QueryParam("szerep") String szerep,@QueryParam("kepesitesid") int kepId) {
        //ObjectNode resp = mapper.createObjectNode();
        try{
            Response success = as.createUser(fnev, nev, password, szerep, kepId);

            return success;
        }catch(ServiceException ex){
            System.out.println(ex);
            switch(ex.getCode()){
                case "database":return Response.status(Response.Status.EXPECTATION_FAILED).entity(ex.getMessage()).build();
                default:return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(ex.getCode()).build();
            }
        }
    }
    
}
