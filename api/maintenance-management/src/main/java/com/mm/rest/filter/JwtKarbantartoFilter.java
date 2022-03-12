/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mm.rest.helper.Constants;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import javax.ws.rs.container.PreMatching;
/**
 *
 * @author david
 */
@Provider
@JwtKarbantarto
public class JwtKarbantartoFilter implements ContainerRequestFilter{
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                                        .entity("Authorization header must be provided OR Authorization header has to start with 'Bearer '(Bearer JWT_String)")
                                        .build()
                                    );
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            // Validate the token
            Algorithm algorithm = Algorithm.HMAC256(Constants.JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
            DecodedJWT jwt = verifier.verify(token);
            
            if(jwt.getClaim("role").asString().equals(Constants.UserRoles.ADMIN) || jwt.getClaim("role").asString().equals(Constants.UserRoles.KARBANTARTO)){
                requestContext.getHeaders().add("id", jwt.getClaim("id").asInt().toString());
                requestContext.getHeaders().add("role", jwt.getClaim("role").asString());
            }else{
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("You are not authorized to reach this endpoint").build());
            }
        } catch (JWTVerificationException exception) {
            //System.out.println("#### invalid token : " + token);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid Json Web Token(JWT) in Authorization header").build());
        }
    }
}
