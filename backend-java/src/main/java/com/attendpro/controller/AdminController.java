package com.attendpro.controller;

import com.attendpro.dto.LoginRequest;
import com.attendpro.dto.LoginResponse;
import com.attendpro.entity.Admin;
import com.attendpro.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = adminService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new LoginResponse(false, "Login failed: " + e.getMessage()));
        }
    }
    
    @GetMapping("/dashboard-stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        try {
            Map<String, Object> stats = adminService.getDashboardStats();
            return ResponseEntity.ok(Map.of("success", true, "data", stats));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getUsers() {
        try {
            Map<String, Object> users = adminService.getAllUsers();
            return ResponseEntity.ok(Map.of("success", true, "data", users));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody Map<String, Object> userData) {
        try {
            Map<String, Object> result = adminService.addUser(userData);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @PutMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> userData) {
        try {
            Map<String, Object> result = adminService.updateUser(id, userData);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        try {
            Map<String, Object> result = adminService.deleteUser(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @GetMapping("/classes")
    public ResponseEntity<Map<String, Object>> getClasses() {
        try {
            Map<String, Object> classes = adminService.getAllClasses();
            return ResponseEntity.ok(Map.of("success", true, "data", classes));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @PostMapping("/classes")
    public ResponseEntity<Map<String, Object>> addClass(@RequestBody Map<String, Object> classData) {
        try {
            Map<String, Object> result = adminService.addClass(classData);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @GetMapping("/subjects")
    public ResponseEntity<Map<String, Object>> getSubjects() {
        try {
            Map<String, Object> subjects = adminService.getAllSubjects();
            return ResponseEntity.ok(Map.of("success", true, "data", subjects));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @PostMapping("/subjects")
    public ResponseEntity<Map<String, Object>> addSubject(@RequestBody Map<String, Object> subjectData) {
        try {
            Map<String, Object> result = adminService.addSubject(subjectData);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @GetMapping("/notices")
    public ResponseEntity<Map<String, Object>> getNotices() {
        try {
            Map<String, Object> notices = adminService.getAllNotices();
            return ResponseEntity.ok(Map.of("success", true, "data", notices));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @PostMapping("/notices")
    public ResponseEntity<Map<String, Object>> addNotice(@RequestBody Map<String, Object> noticeData) {
        try {
            Map<String, Object> result = adminService.addNotice(noticeData);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @GetMapping("/system-settings")
    public ResponseEntity<Map<String, Object>> getSystemSettings() {
        try {
            Map<String, Object> settings = adminService.getSystemSettings();
            return ResponseEntity.ok(Map.of("success", true, "data", settings));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @PutMapping("/system-settings")
    public ResponseEntity<Map<String, Object>> updateSystemSettings(@RequestBody Map<String, Object> settings) {
        try {
            Map<String, Object> result = adminService.updateSystemSettings(settings);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
