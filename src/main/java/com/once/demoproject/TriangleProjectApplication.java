package com.once.demoproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TriangleProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TriangleProjectApplication.class, args);
    }

}
