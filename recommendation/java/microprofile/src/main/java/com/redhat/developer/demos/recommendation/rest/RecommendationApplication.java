package com.redhat.developer.demos.recommendation.rest;

import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.servers.ServerVariable;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

// Swagger UI: http://localhost:8080/openapi-ui/index.html
@ApplicationPath("/")
@OpenAPIDefinition(
        info = @Info(
                title = "Recommendation Service",
                version = "1.0.0",
                description = "Recommendation API",
                license = @License(
                        name = "Tutorial License",
                        url = "https://foo.bar/license"),
                contact = @Contact(
                        email = "foobar@foo.bar",
                        name = "Foo",
                        url = "https://foo.bar"),
                termsOfService = "https://foo.bar/terms"),
        security = @SecurityRequirement(
                name = "oauth2",
                scopes = "read:foobar"),
        externalDocs = @ExternalDocumentation(
                description = "Additional information",
                url = "https://bar.foo/info"),
        servers = @Server(
                description = "Foo description",
                url = "http://localhost:8080"
        ),
        tags = @Tag(
                name = "Type",
                description = "API type")
)
public class RecommendationApplication extends Application {

    public RecommendationApplication() {
    }
}