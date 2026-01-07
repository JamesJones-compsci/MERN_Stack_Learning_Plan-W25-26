package com.classmate.courseservice;

import com.classmate.courseservice.dto.CourseRequestDTO;
import com.classmate.courseservice.model.Course;
import com.classmate.courseservice.repository.CourseRepository;
import com.classmate.courseservice.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class CourseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtToken;
    private String userEmail = "testuser@example.com";

    @BeforeEach
    void setUp() {
        courseRepository.deleteAll();
        // Generate a JWT for the "testuser@example.com"
        jwtToken = jwtService.generateToken(userEmail);
    }

    @Test
    void createCourse_shouldReturnCreatedCourse() throws Exception {
        CourseRequestDTO request = new CourseRequestDTO("CS101", "Intro to CS", "Basics of CS");

        mockMvc.perform(post("/api/courses")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CS101"))
                .andExpect(jsonPath("$.title").value("Intro to CS"))
                .andExpect(jsonPath("$.description").value("Basics of CS"));
    }

    @Test
    void getAllCourses_shouldReturnCoursesForUser() throws Exception {
        courseRepository.save(new Course("CS101", "Intro to CS", "Basics of CS", userEmail));
        courseRepository.save(new Course("CS102", "Data Structures", "DS basics", userEmail));
        courseRepository.save(new Course("CS103", "Algorithms", "Algo basics", "otheruser@example.com"));

        mockMvc.perform(get("/api/courses")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void updateCourse_shouldModifyExistingCourse() throws Exception {
        Course saved = courseRepository.save(new Course("CS101", "Intro", "Old desc", userEmail));

        CourseRequestDTO update = new CourseRequestDTO("CS101", "Intro Updated", "Updated desc");

        mockMvc.perform(put("/api/courses/" + saved.getId())
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Intro Updated"))
                .andExpect(jsonPath("$.description").value("Updated desc"));
    }

    @Test
    void deleteCourse_shouldRemoveCourse() throws Exception {
        Course saved = courseRepository.save(new Course("CS101", "Intro", "Desc", userEmail));

        mockMvc.perform(delete("/api/courses/" + saved.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());

        // Verify it's gone
        mockMvc.perform(get("/api/courses")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void unauthorizedAccess_shouldReturn401() throws Exception {
        mockMvc.perform(get("/api/courses")) // No token
                .andExpect(status().isUnauthorized());
    }
}
