package com.classmate.taskservice.dto;

import lombok.Data;

@Data
public class TaskRequestDTO {
    private String code;
    private String title;
    private String description;
    private boolean completed;

}