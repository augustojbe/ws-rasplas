package com.augustojbe.client.conf;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Api de controle de usuario da Rasmoo-Plus")
                        .description("Gerenciamento de assinaturas de clinete da Rasmoo Plus")
                        .version("0.0.1")
                        .contact(new Contact()
                                .name("Augusto Alves")
                                .email("augustojbe@gmail.com"))
                        .license(new License().url("\"augustojbe@gmail.com\""))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio do GitHub")
                        .url("https://github.com/augustojbe"));
    }




}
