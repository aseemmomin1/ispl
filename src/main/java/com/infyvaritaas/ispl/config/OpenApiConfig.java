package com.infyvaritaas.ispl.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "ISPL Device Repair Services API",
        version = "1.0",
        description = "Device repair service management, payment workflows, and admin operations for the ISPL platform."
    )
)
@SecurityScheme(
    name = "cookieAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "cookie"
)
public class OpenApiConfig {
}
