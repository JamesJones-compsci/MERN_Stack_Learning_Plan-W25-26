package com.classmate.taskservice.controller;

import com.classmate.taskservice.dto.TaskRequestDTO;
import com.classmate.taskservice.dto.TaskResponseDTO;
import com.classmate.taskservice.model.Task;
import com.classmate.taskservice.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public List<TaskResponseDTO> getTasks(Principal principal) {
        String userEmail = principal.getName();

        return taskService.getTasksForUser(userEmail)
                .stream()
                .map(TaskResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @PostMapping
    public TaskResponseDTO createTask(@RequestBody TaskRequestDTO request,
                                      Principal principal) {
        String userEmail = principal.getName();

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
                                      Principal principal) {
        String userEmail = principal.getName();

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
                           Principal principal) {
        String userEmail = principal.getName();
        taskService.deleteTask(id, userEmail);
    }
}