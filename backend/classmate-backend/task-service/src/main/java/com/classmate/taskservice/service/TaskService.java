package com.classmate.taskservice.service;

import com.classmate.taskservice.exception.ResourceNotFoundException;
import com.classmate.taskservice.model.Task;
import com.classmate.taskservice.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Get all tasks for a specific user
    public List<Task> getTasksForUser(String userEmail) {
        return taskRepository.findByUserEmail(userEmail);
    }

    // Create task for user
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Update task
    public Task updateTask(String id, Task taskDetails, String userEmail) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        if (!task.getUserEmail().equals(userEmail)) {
            throw new ResourceNotFoundException("Task does not belong to this user");
        }

        task.setCode(taskDetails.getCode());
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());

        return taskRepository.save(task);
    }

    // Delete task
    public void deleteTask(String id, String userEmail) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        if (!task.getUserEmail().equals(userEmail)) {
            throw new ResourceNotFoundException("Task does not belong to this user");
        }

        taskRepository.delete(task);
    }
}
