package com.attendpro.controller;

import com.attendpro.dto.LoginRequest;
import com.attendpro.dto.LoginResponse;
import com.attendpro.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/faculty")
@CrossOrigin(origins = "*")
public class FacultyController {
    
    @Autowired
    private FacultyService facultyService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = facultyService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new LoginResponse(false, "Login failed: " + e.getMessage()));
        }
    }
    
    @GetMapping("/subjects")
    public ResponseEntity<Map<String, Object>> getSubjects(@RequestParam Long facultyId) {
        try {
            Map<String, Object> subjects = facultyService.getSubjectsByFaculty(facultyId);
            return ResponseEntity.ok(Map.of("success", true, "data", subjects));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @GetMapping("/students")
    public ResponseEntity<Map<String, Object>> getStudents(@RequestParam Long subjectId) {
        try {
            Map<String, Object> students = facultyService.getStudentsBySubject(subjectId);
            return ResponseEntity.ok(Map.of("success", true, "data", students));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @PostMapping("/attendance")
    public ResponseEntity<Map<String, Object>> saveAttendance(@RequestBody Map<String, Object> attendanceData) {
        try {
            Map<String, Object> result = facultyService.saveAttendance(attendanceData);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @GetMapping("/attendance-report")
    public ResponseEntity<Map<String, Object>> getAttendanceReport(@RequestParam Long subjectId) {
        try {
            Map<String, Object> report = facultyService.getAttendanceReport(subjectId);
            return ResponseEntity.ok(Map.of("success", true, "data", report));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
