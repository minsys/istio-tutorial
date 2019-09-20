package com.redhat.developer.demos.preference.rest;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.Dependent;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Properties;

@Dependent
@RegisterRestClient(baseUri="http://localhost:8080")
@Path("/api")
public interface RecommendationClient {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get() throws ProcessingException;

}
