package com.mm.rest.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mm.rest.models.JsonUser;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;

@Path("/json")
public class JsonResource {

    private static final ObjectMapper mapper = new ObjectMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello() {

        // create a JSON string
        ObjectNode json = mapper.createObjectNode();
        json.put("result", "Jersey JSON example using Jackson 2.x");
        return Response.status(Response.Status.OK).entity(json).build();
    }

    // Object to JSON
    @Path("/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonUser hello(@PathParam("name") String name) {
        return new JsonUser(1, name);
    }

    // A list of objects to JSON
    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonUser> helloList() {
        return Arrays.asList(
                new JsonUser(1, "mkyong"),
                new JsonUser(2, "zilap")
        );
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(JsonUser user) {

        ObjectNode json = mapper.createObjectNode();
        json.put("status", user.getName());
        return Response.status(Response.Status.CREATED).entity(json.toString()).build();

    }

}