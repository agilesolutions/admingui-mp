package com.agilesolutions.quarkus.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@ApplicationPath("/api")
@OpenAPIDefinition(info = @Info(
        title = "DevOps pipelines with Jenkins", 
        version = "1.0.0", 
        contact = @Contact(
                name = "Robert Rong", 
                email = "robert.rong@agile-solutions.ch",
                url = "http://www.agile-solutions.ch")
        ),
        servers = {
            @Server(url = "/jenkins",description = "localhost")
        }
)

public class ApplicationConfig extends Application {

}
