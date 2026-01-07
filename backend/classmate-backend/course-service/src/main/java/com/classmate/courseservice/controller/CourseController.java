package com.classmate.courseservice.controller;

import com.classmate.courseservice.dto.CourseRequestDTO;
import com.classmate.courseservice.dto.CourseResponseDTO;
import com.classmate.courseservice.service.CourseService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public CourseResponseDTO createCourse(@RequestBody CourseRequestDTO requestDTO, Authentication auth) {
        return courseService.createCourse(requestDTO, auth.getName());
    }

    @GetMapping
    public List<CourseResponseDTO> getAllCourses(Authentication auth) {
        return courseService.getAllCourses(auth.getName());
    }

    @PutMapping("/{id}")
    public CourseResponseDTO updateCourse(@PathVariable Long id,
                                          @RequestBody CourseRequestDTO requestDTO,
                                          Authentication auth) {
        return courseService.updateCourse(id, requestDTO, auth.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id, Authentication auth) {
        courseService.deleteCourse(id, auth.getName());
    }
}
