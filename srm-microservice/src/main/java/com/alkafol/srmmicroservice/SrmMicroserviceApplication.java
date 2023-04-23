package com.alkafol.srmmicroservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title="SRM microservice"))
public class SrmMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrmMicroserviceApplication.class, args);
    }

}
