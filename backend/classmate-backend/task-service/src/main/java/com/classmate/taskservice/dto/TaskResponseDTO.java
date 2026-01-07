package com.classmate.taskservice.dto;

import com.classmate.taskservice.model.Task;
import lombok.Data;

@Data
public class TaskResponseDTO {
    private String id;
    private String code;
    private String title;
    private String description;
    private boolean completed;

    public static TaskResponseDTO fromEntity(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setCode(task.getCode());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.isCompleted());
        return dto;
    }
}
