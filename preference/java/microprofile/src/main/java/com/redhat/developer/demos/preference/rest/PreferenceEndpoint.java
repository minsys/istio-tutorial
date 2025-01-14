package com.redhat.developer.demos.preference.rest;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;


@ApplicationScoped
@Path("/")
public class PreferenceEndpoint {

    private static final String RESPONSE_STRING_FORMAT = "preference => %s\n";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    @ConfigProperty(name = "recommendations.api.url", defaultValue = "http://recommendations:8080")
    private String remoteURL;

    @Inject
    @RestClient
    private RecommendationClient recommendationClient;

//    @GET
//    @Produces("text/plain")
//    public Response getPreference(@HeaderParam("user-agent") String userAgent) {
//        try {
//            String recommendation = recommendationClient.get();
//            return Response.ok(recommendation).build();
//        } catch (ProcessingException ex) {
//            logger.warn("Exception trying to get the response from recommendation service.", ex);
//            return Response
//                    .status(Response.Status.SERVICE_UNAVAILABLE)
//                    .entity(String.format(RESPONSE_STRING_FORMAT, ex.getCause().getClass().getSimpleName() + ": " + ex.getCause().getMessage()))
//                    .build();
//        }
//    }

    @GET
    @Produces("text/plain")
    public Response getPreference(@HeaderParam("user-agent") String userAgent) {
        try {
            Client client = ClientBuilder.newClient();
            Response res = client.target(remoteURL).request("text/plain").header("user-agent", userAgent).get();
            if (res.getStatus() == Response.Status.OK.getStatusCode()){
                return Response.ok(String.format(RESPONSE_STRING_FORMAT, res.readEntity(String.class))).build();
            } else {
                logger.warn("Non HTTP 20x trying to get the response from preference service: " + res.getStatus());
                return Response
                        .status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity(String.format(RESPONSE_STRING_FORMAT,
                                String.format("Error: %d - %s", res.getStatus(), res.readEntity(String.class)))
                        )
                        .build();
            }
        } catch (ProcessingException ex) {
            logger.warn("Exception trying to get the response from recommendation service.", ex);
            return Response
                    .status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(String.format(RESPONSE_STRING_FORMAT, ex.getCause().getClass().getSimpleName() + ": " + ex.getCause().getMessage()))
                    .build();
        }
    }
}