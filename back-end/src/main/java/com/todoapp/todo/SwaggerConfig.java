package com.todoapp.todo;


import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Upcycling")
                        .description("Created by DSEN to Medium Tutorials")
                        .version("v1.0")
                        .description("Medium Documentation"));
    }

}
