package com.alkafol.hrsmicroservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(info=@Info(title="HRS microservice"))
public class HrsMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrsMicroserviceApplication.class, args);
    }

}
