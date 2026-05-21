package fr.ekod.cda.ja.tp7.config;

import  io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo(){
        return new OpenAPI()
                .info(new Info()
                        .title("Movie API")
                        .version("1.0.0")
                        .description("API de gestion de films et réalisateurs"));
    }


}
