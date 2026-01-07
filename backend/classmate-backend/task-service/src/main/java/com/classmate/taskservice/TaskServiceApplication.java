package com.classmate.taskservice;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(TaskServiceApplication.class, args);
    }

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @PostConstruct
    public void logMongoUri() {
        System.out.println("Mongo URI: " + mongoUri);
    }

}
