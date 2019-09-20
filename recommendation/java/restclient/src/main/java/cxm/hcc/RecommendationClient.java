package cxm.hcc;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.Dependent;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Properties;

@Dependent
@RegisterRestClient
@Path("/properties")
public interface RecommendationClient {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Properties getProperties() throws ProcessingException;

}
