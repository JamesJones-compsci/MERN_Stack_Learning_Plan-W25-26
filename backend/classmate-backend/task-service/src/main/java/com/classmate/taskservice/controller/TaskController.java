package com.classmate.taskservice.controller;

import com.classmate.taskservice.dto.TaskRequestDTO;
import com.classmate.taskservice.dto.TaskResponseDTO;
import com.classmate.taskservice.model.Task;
import com.classmate.taskservice.service.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponseDTO> getTasks(@AuthenticationPrincipal Jwt jwt) {
        String userEmail = jwt.getSubject();
        return taskService.getTasksForUser(userEmail)
                .stream()
                .map(TaskResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @PostMapping
    public TaskResponseDTO createTask(@RequestBody TaskRequestDTO request,
                                      @AuthenticationPrincipal Jwt jwt) {
        String userEmail = jwt.getSubject();
        Task task = new Task(
                request.getCode(),
                request.getTitle(),
                request.getDescription(),
                userEmail
        );
        Task savedTask = taskService.createTask(task);
        return TaskResponseDTO.fromEntity(savedTask);
    }

    @PutMapping("/{id}")
    public TaskResponseDTO updateTask(@PathVariable String id,
                                      @RequestBody TaskRequestDTO request,
                                      @AuthenticationPrincipal Jwt jwt) {
        String userEmail = jwt.getSubject();
        Task task = new Task(
                request.getCode(),
                request.getTitle(),
                request.getDescription(),
                userEmail
        );
        Task updatedTask = taskService.updateTask(id, task, userEmail);
        return TaskResponseDTO.fromEntity(updatedTask);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id,
                           @AuthenticationPrincipal Jwt jwt) {
        String userEmail = jwt.getSubject();
        taskService.deleteTask(id, userEmail);
    }
}