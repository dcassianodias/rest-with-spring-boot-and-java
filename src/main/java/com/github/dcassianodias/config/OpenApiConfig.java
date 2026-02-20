package com.github.dcassianodias.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("REST API'S RESTful from 0 with Java, SpringBoot, Kubernetes and Docker")
                        .version("v1")
                        .description("API RESTful developed with Java and Spring Boot")
                        .termsOfService("https://www.udemy.com/course/restful-web-services-with-spring-boot-and-java/")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
