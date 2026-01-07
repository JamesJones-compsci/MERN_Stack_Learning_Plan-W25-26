package com.classmate.courseservice.service;

import com.classmate.courseservice.dto.CourseRequestDTO;
import com.classmate.courseservice.dto.CourseResponseDTO;
import com.classmate.courseservice.exception.ResourceNotFoundException;
import com.classmate.courseservice.model.Course;
import com.classmate.courseservice.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public CourseResponseDTO createCourse(CourseRequestDTO requestDTO, String userEmail) {
        Course course = new Course(
                requestDTO.getCode(),
                requestDTO.getTitle(),
                requestDTO.getDescription(),
                userEmail
        );
        Course saved = repository.save(course);
        return mapToDTO(saved);
    }

    public List<CourseResponseDTO> getAllCourses(String userEmail) {
        return repository.findByUserEmail(userEmail)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO requestDTO, String userEmail) {
        Course course = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));

        if (!course.getUserEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized");
        }

        course.setCode(requestDTO.getCode());
        course.setTitle(requestDTO.getTitle());
        course.setDescription(requestDTO.getDescription());

        Course updated = repository.save(course);
        return mapToDTO(updated);
    }

    public void deleteCourse(Long id, String userEmail) {
        Course course = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));

        if (!course.getUserEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized");
        }

        repository.delete(course);
    }

    private CourseResponseDTO mapToDTO(Course course) {
        return new CourseResponseDTO(
                course.getId(),
                course.getCode(),
                course.getTitle(),
                course.getDescription()
        );
    }
}
