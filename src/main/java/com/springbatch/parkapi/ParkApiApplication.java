package com.springbatch.parkapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.springbatch.parkapi.repository")
public class ParkApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkApiApplication.class, args);
    }

}
