package com.mballem.demo_park_api.config;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("REST API - Spring Park")
                        .description("API para gestão de estacionamento")
                        .version("v1")
                        .license(new io.swagger.v3.oas.models.info.License().name("Apache 2.0"))
                );
    }
}
