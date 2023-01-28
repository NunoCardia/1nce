package com.once.triangleproject.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.once.triangleproject")
@EnableJpaRepositories("com.once.triangleproject.mysql.*")
@EntityScan("com.once.triangleproject.mysql.entity")
@EnableJpaAuditing
public class TriangleProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TriangleProjectApplication.class, args);
    }

}
