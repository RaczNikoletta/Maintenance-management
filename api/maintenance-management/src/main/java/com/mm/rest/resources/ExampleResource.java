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
// http://localhost:8080/api/example
@Path("/example")
public class ExampleResource {
    private static final ObjectMapper mapper = new ObjectMapper();
    //private static final Connection con = DbConnection.getConnection();
    private static final TestService tm = new TestService();
    
    // http://localhost:8080/api/example/ex/123
    @GET
    @Path("/ex/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public long getCustomer(@PathParam("id") long id) {
      return id;
    }
    
    // http://localhost:8080/api/example/jsonstring
    @GET
    @Path("/jsonstring")
    @Produces(MediaType.TEXT_PLAIN)
    public String getJsonString() {
      return new Example(1,"Example1").toString();
    }
    
    // http://localhost:8080/api/example/mysql
    /*@GET
    @Path("/mysql")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMysql() {
        if(con == null) return "Error";
        
        try {    
            String resString = "";
            Statement selectStmt = con.createStatement();
            ResultSet rs = selectStmt.executeQuery("SELECT * FROM testtable");
            while(rs.next()){
                resString += rs.getInt(1) + "|";
                resString += rs.getString(2) + "|";
                resString += rs.getString(3) + "|";
                resString += rs.getString(4) + "\r\n";
                System.out.print(rs.getInt(1) + "|");  //First Column
                System.out.print(rs.getString(2) + "|");  //Second Column
                System.out.print(rs.getString(3) + "|");  //Third Column
                System.out.print(rs.getString(4));  //Fourth Column
                System.out.println();
            }
            
            return resString;
        } catch (SQLException ex) {
            Logger.getLogger(ExampleResource.class.getName()).log(Level.SEVERE, null, ex);
            return "SQL Exception Error";
        }
    }*/
    
    // http://localhost:8080/api/example/all
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHibernateAll() {
        
        ObjectNode vendor1 = mapper.createObjectNode();
        
        ArrayNode arrayNode1 = mapper.createArrayNode();
        
        List<TestModel> lt = tm.query();
        for(TestModel tItem:lt){
            arrayNode1.add(mapper.valueToTree(tItem));
        }

        vendor1.set("Array", arrayNode1);
        
        return Response.status(Response.Status.OK).entity(vendor1.toString()).build();
        
    }
    
    // http://localhost:8080/api/example/add-user
    @POST
    @Path("/adduser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(UserContent user) {
        ObjectNode resp = mapper.createObjectNode();
        //User user = new User("dsadsa","dsadsa","dsadsa");
        try{
            System.out.println(user.getName());
            System.out.println(user.getKepzes());
            System.out.println(user.getPassword());

            boolean success = tm.create(user);
            resp.put("success", success);

            return Response.status(Response.Status.OK).entity(resp.toString()).build();
        }catch(Exception ex){
            System.out.println(ex);
            resp.put("success", false);
            return Response.status(Response.Status.OK).entity(resp.toString()).build();
        }
    }
    
    @POST
    @Path("addtest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTest(UserContent user) {
        System.out.println("Do I even get here???");
        ObjectNode resp = mapper.createObjectNode();
        try{
            resp.put("success", mapper.valueToTree(user));
            return Response.status(Response.Status.OK).entity(resp.toString()).build();
        }catch(Exception ex){
            return Response.status(Response.Status.OK).entity(resp.toString()).build();
        }
        
    }
    
    @GET
    @Path("bcrypt/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBcrypt(@PathParam("password") String password) {
        System.out.println("Do I even get here???");
        ObjectNode resp = mapper.createObjectNode();
        //mapper.valueToTree(password)
        resp.put("plain password", password);
        
        String hashedPw = BCrypt.hashpw(password, BCrypt.gensalt());
        resp.put("hashed password", hashedPw);
        
        resp.put("hashed password is password just hashed(True)", BCrypt.checkpw(password, hashedPw));
        resp.put("hashed password is password just hashed(False)", BCrypt.checkpw("This should fail tho", hashedPw));
        
        return Response.status(Response.Status.OK).entity(resp.toString()).build();
        
    }
    
    @GET
    @Path("jwt")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJwt() {
        System.out.println("Do I even get here???");
        ObjectNode resp = mapper.createObjectNode();
        //mapper.valueToTree(password)
        //resp.put("plain password", password);

        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                .withIssuer("auth0")
                .withExpiresAt(toDate(LocalDateTime.now().plusMinutes(5)))
                .withClaim("id", 1)
                .withClaim("role","admin")
                .sign(algorithm);
            resp.put("JWT", token);
            return Response.status(Response.Status.OK).header(AUTHORIZATION, "Bearer " + token).entity(resp.toString()).build();
        } catch (JWTCreationException exception){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    @GET
    @Path("jwtquick")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJwtQuick() {
        System.out.println("Do I even get here???");
        ObjectNode resp = mapper.createObjectNode();
        //mapper.valueToTree(password)
        //resp.put("plain password", password);

        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                .withIssuer("auth0")
                .withExpiresAt(toDate(LocalDateTime.now().plusMinutes(1)))
                .withClaim("id", 1)
                .withClaim("role","admin")
                .sign(algorithm);
            resp.put("JWT", token);
            return Response.status(Response.Status.OK).header(AUTHORIZATION, "Bearer " + token).entity(resp.toString()).build();
        } catch (JWTCreationException exception){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    @GET
    @Path("jwtneeded")
    @JwtTokenNeeded
    public Response getJwtNeeded(@Context HttpHeaders httpheaders) {
        ObjectNode resp = mapper.createObjectNode();
        JwtProperties jwt = new JwtProperties(httpheaders.getHeaderString("id"),httpheaders.getHeaderString("role"));
        
        resp.put("role",jwt.getRole());
        resp.put("id",jwt.getId());
        
        return Response.status(Response.Status.OK).entity(resp.toString()).build();
    }
    
    // http://localhost:8080/api/example/jsonresponse
    @GET
    @Path("/jsonresponse")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson2() {
        ObjectNode json = mapper.createObjectNode();
        json = mapper.valueToTree(new Example(3,"Example3"));
        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }
    
    // http://localhost:8080/api/example/jsonresponse2
    @GET
    @Path("/jsonresponse2")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson3() {
        ObjectNode json = mapper.createObjectNode();
        
        json.put("example_key", "example_data");
        json.put("example_key2", "123");
        
        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }
    
    // http://localhost:8080/api/example/jsonresponse3
    @GET
    @Path("/jsonresponse3")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson4() {

        ObjectNode example1 = mapper.createObjectNode();
        example1 = mapper.valueToTree(new Example(1,"Example1"));
        
        ObjectNode example2 = mapper.createObjectNode();
        example2 = mapper.valueToTree(new Example(2,"Example2"));
        
        ObjectNode example3 = mapper.createObjectNode();
        example3 = mapper.valueToTree(new Example(3,"Example3"));
        
        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.addAll(Arrays.asList(example1,example2,example3));
        
        
        return Response.status(Response.Status.OK).entity(arrayNode.toString()).build();
    }
    
    // http://localhost:8080/api/example/jsonresponse4
    @GET
    @Path("/jsonresponse4")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson5() {
        
        ObjectNode vendor1 = mapper.createObjectNode();
        vendor1.put("example key", "EXAMPLE1");

        ObjectNode vendor2 = mapper.createObjectNode();
        vendor2.put("example key", "EXAMPLE2");

        ObjectNode vendor3 = mapper.createObjectNode();
        vendor3.put("example key", "EXAMPLE3");
        
        ArrayNode arrayNode1 = mapper.createArrayNode();
        arrayNode1.add(mapper.valueToTree(new Example(11,"Example11")));
        arrayNode1.add(mapper.valueToTree(new Example(12,"Example12")));
        arrayNode1.add(mapper.valueToTree(new Example(13,"Example13")));
        
        ArrayNode arrayNode2 = mapper.createArrayNode();
        arrayNode2.add(mapper.valueToTree(new Example(21,"Example21")));
        arrayNode2.add(mapper.valueToTree(new Example(22,"Example22")));
        arrayNode2.add(mapper.valueToTree(new Example(23,"Example23")));
        
        ArrayNode arrayNode3 = mapper.createArrayNode();
        arrayNode3.add(mapper.valueToTree(new Example(31,"Example31")));
        arrayNode3.add(mapper.valueToTree(new Example(32,"Example32")));
        arrayNode3.add(mapper.valueToTree(new Example(33,"Example33")));
        
        vendor1.set("ex", arrayNode1);
        vendor2.set("ex", arrayNode2);
        vendor3.set("ex", arrayNode3);
        
        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.addAll(Arrays.asList(vendor1,vendor2,vendor3));
        
        return Response.status(Response.Status.OK).entity(arrayNode.toString()).build();
    }
    
    // http://localhost:8080/api/example/jsonresponse5
    @GET
    @Path("/jsonresponse5")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson6() {
        
        ObjectNode vendor1 = mapper.createObjectNode();
        
        ArrayNode arrayNode1 = mapper.createArrayNode();
        arrayNode1.add(mapper.valueToTree(new Example(1,"Example1")));
        arrayNode1.add(mapper.valueToTree(new Example(2,"Example2")));
        arrayNode1.add(mapper.valueToTree(new Example(3,"Example3")));
        
        vendor1.set("Array", arrayNode1);
        
        return Response.status(Response.Status.OK).entity(vendor1.toString()).build();
    }
    
    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
