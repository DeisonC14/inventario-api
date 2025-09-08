package com.almacen.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
		name = "bearerAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT"
		)
public class OpenApiConfig {
	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
			.info(new Info()
			.title("API DE ALMACEN")
			.version("1.0.0")
			.description("Documentación de la API para el sistema de un almacen.")
			.contact(new Contact()
			.name("Soporte Técnico")
			.email("soporte@almacen.com")
			.url("https://almacen.com")) );
	}
}
