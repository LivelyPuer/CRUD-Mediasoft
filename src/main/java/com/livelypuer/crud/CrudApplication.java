package com.livelypuer.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Main class of the Spring Boot application.
 */
@SpringBootApplication()
@EntityScan("com.livelypuer.crud.entities")
@EnableJpaRepositories("com.livelypuer.crud.repositories")
//@EnableSwagger2
public class CrudApplication {
    /**
     * Main method of the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }
    /**
     * Example endpoint that returns a greeting.
     *
     * @param name the name of the person to greet
     * @return the greeting
     */
    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

}