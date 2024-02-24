package com.example.sosinventory.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "SOS LIFESTYLE",
                        email = "soslifestyle@gmail.com",
                        url = "https://soslifestyle.com"),
                description = "OpenApi documentation for SOS LIFESTYLE",
                title = "OpenApi specification - SOS LIFESTYLE",
                version = "1.0",
                license = @License(
                        name = "All Rights Reserved",
                        url = "https://ballers.com"
                ),
                termsOfService = "Terms of service..."
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "STAGING ENV",
                        url = "http://54.167.64.74:9998"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://soslifestyle.com"
                )

        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
