package dev.kiki.walletapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Nishimwe Rukundo Prosper",
                        email = "prosper.rk1@gmail.com"
                ),
                description = "API Documentation for a wallet app (Budget Buddy)",
                title = "Budget Buddy API",
                version = "v.1.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Production ENV",
                        url = "https://wallet-api-oi3v.onrender.com"
                )
        }
)
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "auth",
        description = "JWT Authentication",
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
