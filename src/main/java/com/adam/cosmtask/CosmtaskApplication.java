package com.adam.cosmtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class CosmtaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(CosmtaskApplication.class, args);
    }

}
