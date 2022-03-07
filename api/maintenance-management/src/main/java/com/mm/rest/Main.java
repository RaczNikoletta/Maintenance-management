package com.mm.rest;

import com.mm.rest.resources.ExampleResource;
import com.mm.rest.resources.JsonResource;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/api/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.mm.rest.resources package
        // cmd-be: mvn clean compile
        // cmd-be: mvn exec:java
        final ResourceConfig rc = new ResourceConfig();
        rc.packages("com.mm.rest");
        //rc.packages("com.mm.rest.filter");
        /*rc.register(ExampleResource.class);
        rc.register(JsonResource.class);*/
        rc.register(JacksonFeature.class);
        //rc.property("TRACING", "ALL");
                                                      

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

