package com.sylviavitoria.blogpets.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${api.version}")
    private String apiVersion;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("API de BlogPets")
                        .description("API REST para gerenciamento de Blogs para Pets")
                        .version(apiVersion))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor Local")));
    }
}