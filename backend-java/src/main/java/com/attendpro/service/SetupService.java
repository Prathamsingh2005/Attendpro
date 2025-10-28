package com.attendpro.service;

import com.attendpro.entity.*;
import com.attendpro.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class SetupService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private FacultyRepository facultyRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private ClassRepository classRepository;
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private NoticeRepository noticeRepository;
    
    @Autowired
    private SystemSettingsRepository systemSettingsRepository;
    
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Transactional
    public Map<String, Object> setupDatabase() {
        try {
            // Check if admin already exists
            if (adminRepository.count() > 0) {
                return Map.of("success", true, "message", "Database already set up!");
            }
            
            // Create default admin user
            createDefaultAdmin();
            
            // Create system settings
            createSystemSettings();
            
            // Create sample classes
            createSampleClasses();
            
            // Create sample subjects
            createSampleSubjects();
            
            return Map.of("success", true, "message", "Database setup completed successfully!");
            
        } catch (Exception e) {
            return Map.of("success", false, "message", "Database setup failed: " + e.getMessage());
        }
    }
    
    @Transactional
    public Map<String, Object> createSampleData() {
        try {
            // Create sample faculty
            createSampleFaculty();
            
            // Create sample students
            createSampleStudents();
            
            // Create sample notices
            createSampleNotices();
            
            return Map.of("success", true, "message", "Sample data created successfully!");
            
        } catch (Exception e) {
            return Map.of("success", false, "message", "Sample data creation failed: " + e.getMessage());
        }
    }
    
    public Map<String, Object> testSystem() {
        Map<String, Object> results = new HashMap<>();
        List<Map<String, Object>> tests = new ArrayList<>();
        
        // Test database connection
        try {
            long adminCount = adminRepository.count();
            tests.add(Map.of("name", "Database Connection", "status", "PASS", "message", "Connected successfully"));
        } catch (Exception e) {
            tests.add(Map.of("name", "Database Connection", "status", "FAIL", "message", e.getMessage()));
        }
        
        // Test admin API
        try {
            if (adminRepository.count() > 0) {
                tests.add(Map.of("name", "Admin API", "status", "PASS", "message", "Admin API working"));
            } else {
                tests.add(Map.of("name", "Admin API", "status", "FAIL", "message", "No admin user found"));
            }
        } catch (Exception e) {
            tests.add(Map.of("name", "Admin API", "status", "FAIL", "message", e.getMessage()));
        }
        
        // Test faculty functionality
        try {
            long facultyCount = facultyRepository.count();
            tests.add(Map.of("name", "Faculty System", "status", facultyCount > 0 ? "PASS" : "WARN", 
                           "message", facultyCount > 0 ? "Faculty system ready" : "No faculty found"));
        } catch (Exception e) {
            tests.add(Map.of("name", "Faculty System", "status", "FAIL", "message", e.getMessage()));
        }
        
        // Test student functionality
        try {
            long studentCount = studentRepository.count();
            tests.add(Map.of("name", "Student System", "status", studentCount > 0 ? "PASS" : "WARN", 
                           "message", studentCount > 0 ? "Student system ready" : "No students found"));
        } catch (Exception e) {
            tests.add(Map.of("name", "Student System", "status", "FAIL", "message", e.getMessage()));
        }
        
        results.put("tests", tests);
        results.put("success", true);
        
        return results;
    }
    
    public Map<String, Object> getSystemStatus() {
        Map<String, Object> status = new HashMap<>();
        
        try {
            status.put("database_connected", true);
            status.put("admin_count", adminRepository.count());
            status.put("faculty_count", facultyRepository.count());
            status.put("student_count", studentRepository.count());
            status.put("class_count", classRepository.count());
            status.put("subject_count", subjectRepository.count());
            status.put("notice_count", noticeRepository.count());
            status.put("attendance_count", attendanceRepository.count());
            status.put("system_ready", adminRepository.count() > 0);
            status.put("success", true);
        } catch (Exception e) {
            status.put("database_connected", false);
            status.put("error", e.getMessage());
            status.put("success", false);
        }
        
        return status;
    }
    
    @Transactional
    public Map<String, Object> resetSystem() {
        try {
            // Clear all data except admin
            attendanceRepository.deleteAll();
            noticeRepository.deleteAll();
            subjectRepository.deleteAll();
            studentRepository.deleteAll();
            facultyRepository.deleteAll();
            classRepository.deleteAll();
            systemSettingsRepository.deleteAll();
            
            // Recreate system settings
            createSystemSettings();
            
            return Map.of("success", true, "message", "System reset successfully!");
            
        } catch (Exception e) {
            return Map.of("success", false, "message", "System reset failed: " + e.getMessage());
        }
    }
    
    private void createDefaultAdmin() {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setName("System Administrator");
        admin.setEmail("admin@attendpro.com");
        adminRepository.save(admin);
    }
    
    private void createSystemSettings() {
        List<SystemSettings> settings = Arrays.asList(
            new SystemSettings("institution_name", "AttendPro Institution", "Name of the educational institution"),
            new SystemSettings("default_attendance_threshold", "75", "Default attendance percentage threshold"),
            new SystemSettings("session_timeout", "30", "Session timeout in minutes"),
            new SystemSettings("email_notifications", "enabled", "Enable/disable email notifications"),
            new SystemSettings("academic_year", "2024-25", "Current academic year"),
            new SystemSettings("max_attendance_per_day", "8", "Maximum attendance sessions per day"),
            new SystemSettings("backup_frequency", "daily", "Automatic backup frequency")
        );
        
        systemSettingsRepository.saveAll(settings);
    }
    
    private void createSampleClasses() {
        List<com.attendpro.entity.Class> classes = Arrays.asList(
            new com.attendpro.entity.Class("Class 1", "A", "2024-25"),
            new com.attendpro.entity.Class("Class 2", "A", "2024-25"),
            new com.attendpro.entity.Class("Class 3", "A", "2024-25"),
            new com.attendpro.entity.Class("Class 4", "A", "2024-25"),
            new com.attendpro.entity.Class("Class 5", "A", "2024-25")
        );
        
        classRepository.saveAll(classes);
    }
    
    private void createSampleSubjects() {
        List<Subject> subjects = Arrays.asList(
            new Subject("Mathematics", "MATH101", 1L, null, 4),
            new Subject("English", "ENG101", 1L, null, 3),
            new Subject("Science", "SCI101", 1L, null, 4),
            new Subject("Social Studies", "SS101", 1L, null, 3),
            new Subject("Computer Science", "CS101", 2L, null, 4)
        );
        
        subjectRepository.saveAll(subjects);
    }
    
    private void createSampleFaculty() {
        List<Faculty> faculty = Arrays.asList(
            new Faculty("Dr. John Smith", "john.smith@attendpro.com", passwordEncoder.encode("faculty123")),
            new Faculty("Dr. Jane Doe", "jane.doe@attendpro.com", passwordEncoder.encode("faculty123")),
            new Faculty("Prof. Mike Johnson", "mike.johnson@attendpro.com", passwordEncoder.encode("faculty123"))
        );
        
        faculty.get(0).setUsername("john.smith");
        faculty.get(0).setDepartment("Mathematics");
        faculty.get(1).setUsername("jane.doe");
        faculty.get(1).setDepartment("English");
        faculty.get(2).setUsername("mike.johnson");
        faculty.get(2).setDepartment("Computer Science");
        
        facultyRepository.saveAll(faculty);
    }
    
    private void createSampleStudents() {
        List<Student> students = Arrays.asList(
            new Student("STU001", "Alice Johnson", "alice.johnson@student.com", passwordEncoder.encode("student123")),
            new Student("STU002", "Bob Smith", "bob.smith@student.com", passwordEncoder.encode("student123")),
            new Student("STU003", "Charlie Brown", "charlie.brown@student.com", passwordEncoder.encode("student123")),
            new Student("STU004", "Diana Prince", "diana.prince@student.com", passwordEncoder.encode("student123")),
            new Student("STU005", "Eve Wilson", "eve.wilson@student.com", passwordEncoder.encode("student123"))
        );
        
        for (Student student : students) {
            student.setDepartment("Computer Science");
            student.setSemester("1");
            student.setClassId(1L);
        }
        
        studentRepository.saveAll(students);
    }
    
    private void createSampleNotices() {
        Admin admin = adminRepository.findByUsername("admin").orElse(null);
        if (admin != null) {
            List<Notice> notices = Arrays.asList(
                new Notice("Welcome to AttendPro", "Welcome to the AttendPro system. Please explore all features.", 
                          TargetAudience.ALL, null, NoticePriority.HIGH, NoticeStatus.PUBLISHED, admin.getId()),
                new Notice("Attendance Policy", "Please maintain 75% attendance to be eligible for exams.", 
                          TargetAudience.STUDENTS, null, NoticePriority.MEDIUM, NoticeStatus.PUBLISHED, admin.getId()),
                new Notice("Faculty Meeting", "Monthly faculty meeting scheduled for next Friday.", 
                          TargetAudience.FACULTY, null, NoticePriority.MEDIUM, NoticeStatus.PUBLISHED, admin.getId())
            );
            
            noticeRepository.saveAll(notices);
        }
    }
}
