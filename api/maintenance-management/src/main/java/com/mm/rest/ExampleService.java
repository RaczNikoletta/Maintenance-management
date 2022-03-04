/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Arrays;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author david
 */
// http://localhost:8080/api/example
@Path("/example")
public class ExampleService {
    private static final ObjectMapper mapper = new ObjectMapper();
    
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
}
