package com.adam.cosmose;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class CosmoseApplication {

    public static void main(String[] args) {
        SpringApplication.run(CosmoseApplication.class, args);
    }

}
