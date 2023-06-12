package com.once.triangleproject.application;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.MountableFile;

import java.io.IOException;

@SpringBootTest
public abstract class BaseTest {

    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:5.7")
            .withReuse(true)
            .withCopyFileToContainer(MountableFile.forClasspathResource("mysql/schema.sql"), "/docker-entrypoint-initdb.d/");

    @DynamicPropertySource
    public static void setup(DynamicPropertyRegistry registry) throws IOException {
        Startables.deepStart(mySQLContainer).join();
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

}
