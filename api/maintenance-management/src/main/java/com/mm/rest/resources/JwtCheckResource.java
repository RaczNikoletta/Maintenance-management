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
import com.mm.rest.filter.JwtAdmin;
import com.mm.rest.filter.JwtEszkozfelelos;
import com.mm.rest.filter.JwtKarbantarto;
import com.mm.rest.filter.JwtOperator;
import com.mm.rest.filter.JwtTokenNeeded;
import com.mm.rest.helper.JwtProperties;
import com.mm.rest.service.TestService;
import com.mm.rest.models.TestModel;
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

//Csak peldak hogyan kell a védett routeokat megcsinalni, és tesztnek
// http://localhost:8080/api/jwt
@Path("/jwt")
public class JwtCheckResource {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final TestService tm = new TestService();
    
    @GET
    @Path("basic")
    @JwtTokenNeeded
    public Response getJwt(@Context HttpHeaders httpheaders) {
        ObjectNode resp = mapper.createObjectNode();
        JwtProperties jwt = new JwtProperties(httpheaders.getHeaderString("id"),httpheaders.getHeaderString("role"));
        
        resp.put("role",jwt.getRole());
        resp.put("id",jwt.getId());
        resp.put("authRoute", "Basic jwt needed route");
        
        return Response.status(Response.Status.OK).entity(resp.toString()).build();
    }
    
    @GET
    @Path("admin")
    @JwtAdmin
    public Response getAdmin(@Context HttpHeaders httpheaders) {
        ObjectNode resp = mapper.createObjectNode();
        JwtProperties jwt = new JwtProperties(httpheaders.getHeaderString("id"),httpheaders.getHeaderString("role"));
        
        resp.put("role",jwt.getRole());
        resp.put("id",jwt.getId());
        resp.put("authRoute", "Admin route");
        
        return Response.status(Response.Status.OK).entity(resp.toString()).build();
    }
    
    @GET
    @Path("eszkozfelelos")
    @JwtEszkozfelelos
    public Response getEszkozfelelos(@Context HttpHeaders httpheaders) {
        ObjectNode resp = mapper.createObjectNode();
        JwtProperties jwt = new JwtProperties(httpheaders.getHeaderString("id"),httpheaders.getHeaderString("role"));
        
        resp.put("role",jwt.getRole());
        resp.put("id",jwt.getId());
        resp.put("authRoute", "Eszkozfelelos route");
        
        return Response.status(Response.Status.OK).entity(resp.toString()).build();
    }
    
    @GET
    @Path("karbantarto")
    @JwtKarbantarto
    public Response getJwtNeeded(@Context HttpHeaders httpheaders) {
        ObjectNode resp = mapper.createObjectNode();
        JwtProperties jwt = new JwtProperties(httpheaders.getHeaderString("id"),httpheaders.getHeaderString("role"));
        
        resp.put("role",jwt.getRole());
        resp.put("id",jwt.getId());
        resp.put("authRoute", "Karbantarto route");
        
        return Response.status(Response.Status.OK).entity(resp.toString()).build();
    }
    
    @GET
    @Path("operator")
    @JwtOperator
    public Response getOperator(@Context HttpHeaders httpheaders) {
        ObjectNode resp = mapper.createObjectNode();
        JwtProperties jwt = new JwtProperties(httpheaders.getHeaderString("id"),httpheaders.getHeaderString("role"));
        
        resp.put("role",jwt.getRole());
        resp.put("id",jwt.getId());
        resp.put("authRoute", "Operator route");
        
        return Response.status(Response.Status.OK).entity(resp.toString()).build();
    }
}
