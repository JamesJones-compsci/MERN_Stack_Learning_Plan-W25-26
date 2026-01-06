package com.classmate.taskservice;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(TaskServiceApplication.class, args);
    }

    @PostConstruct
    public void logMongoUri() {
        System.out.println("Mongo URI: " + System.getProperty("spring.data.mongodb.uri"));
    }

}
