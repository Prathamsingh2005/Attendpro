package com.attendpro.controller;

import com.attendpro.service.SetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/setup")
@CrossOrigin(origins = "*")
public class SetupController {
    
    @Autowired
    private SetupService setupService;
    
    @PostMapping("/database")
    public ResponseEntity<Map<String, Object>> setupDatabase() {
        try {
            Map<String, Object> result = setupService.setupDatabase();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Database setup failed: " + e.getMessage()));
        }
    }
    
    @PostMapping("/sample-data")
    public ResponseEntity<Map<String, Object>> createSampleData() {
        try {
            Map<String, Object> result = setupService.createSampleData();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Sample data creation failed: " + e.getMessage()));
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> testSystem() {
        try {
            Map<String, Object> result = setupService.testSystem();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "System test failed: " + e.getMessage()));
        }
    }
    
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getSystemStatus() {
        try {
            Map<String, Object> result = setupService.getSystemStatus();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Status check failed: " + e.getMessage()));
        }
    }
    
    @PostMapping("/reset")
    public ResponseEntity<Map<String, Object>> resetSystem() {
        try {
            Map<String, Object> result = setupService.resetSystem();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "System reset failed: " + e.getMessage()));
        }
    }
}
