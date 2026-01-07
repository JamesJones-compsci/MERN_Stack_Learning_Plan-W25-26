package com.classmate.taskservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    private String id;

    private String code;        // optional if you want task codes
    private String title;
    private String description;
    private String userEmail;   // links task to authenticated user
    private boolean completed = false;


    // Constructors
    public Task(String code, String title, String description, String userEmail) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.userEmail = userEmail;
        this.completed = false;


    }
}
