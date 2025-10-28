package com.attendpro.controller;

import com.attendpro.dto.LoginRequest;
import com.attendpro.dto.LoginResponse;
import com.attendpro.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = studentService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new LoginResponse(false, "Login failed: " + e.getMessage()));
        }
    }
    
    @GetMapping("/attendance")
    public ResponseEntity<Map<String, Object>> getStudentAttendance(@RequestParam Long studentId) {
        try {
            Map<String, Object> attendance = studentService.getStudentAttendance(studentId);
            return ResponseEntity.ok(Map.of("success", true, "data", attendance));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @GetMapping("/notices")
    public ResponseEntity<Map<String, Object>> getNoticesForStudent(@RequestParam(required = false) Long classId) {
        try {
            Map<String, Object> notices = studentService.getNoticesForStudent(classId);
            return ResponseEntity.ok(Map.of("success", true, "data", notices));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getStudentProfile(@RequestParam Long studentId) {
        try {
            Map<String, Object> profile = studentService.getStudentProfile(studentId);
            return ResponseEntity.ok(Map.of("success", true, "data", profile));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @GetMapping("/grades")
    public ResponseEntity<Map<String, Object>> getStudentGrades(@RequestParam Long studentId) {
        try {
            Map<String, Object> grades = studentService.getStudentGrades(studentId);
            return ResponseEntity.ok(Map.of("success", true, "data", grades));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @GetMapping("/schedule")
    public ResponseEntity<Map<String, Object>> getStudentSchedule(@RequestParam Long studentId) {
        try {
            Map<String, Object> schedule = studentService.getStudentSchedule(studentId);
            return ResponseEntity.ok(Map.of("success", true, "data", schedule));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @GetMapping("/faculty-notes")
    public ResponseEntity<Map<String, Object>> getFacultyNotes(@RequestParam Long studentId) {
        try {
            Map<String, Object> notes = studentService.getFacultyNotes(studentId);
            return ResponseEntity.ok(Map.of("success", true, "data", notes));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @GetMapping("/dashboard-stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats(@RequestParam Long studentId) {
        try {
            Map<String, Object> stats = studentService.getDashboardStats(studentId);
            return ResponseEntity.ok(Map.of("success", true, "data", stats));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
