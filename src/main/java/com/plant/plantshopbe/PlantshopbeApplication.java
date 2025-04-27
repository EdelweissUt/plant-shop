package com.plant.plantshopbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PlantshopbeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlantshopbeApplication.class, args);
    }

}
