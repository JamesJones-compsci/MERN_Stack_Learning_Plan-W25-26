package com.classmate.taskservice.repository;

import com.classmate.taskservice.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByUserEmail(String userEmail);  // returns tasks for a specific user
}
