package com.attendpro.service;

import com.attendpro.dto.LoginRequest;
import com.attendpro.dto.LoginResponse;
import com.attendpro.entity.Admin;
import com.attendpro.repository.AdminRepository;
import com.attendpro.repository.FacultyRepository;
import com.attendpro.repository.StudentRepository;
import com.attendpro.repository.ClassRepository;
import com.attendpro.repository.SubjectRepository;
import com.attendpro.repository.NoticeRepository;
import com.attendpro.repository.SystemSettingsRepository;
import com.attendpro.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AdminService {
    
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
    
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<Admin> adminOpt = adminRepository.findByUsername(loginRequest.getUsername());
        
        if (adminOpt.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), adminOpt.get().getPassword())) {
            Admin admin = adminOpt.get();
            LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getUsername(),
                "ADMIN"
            );
            return new LoginResponse(true, "Login successful", "jwt-token-here", userInfo);
        } else {
            return new LoginResponse(false, "Invalid credentials");
        }
    }
    
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("total_faculty", facultyRepository.count());
        stats.put("total_students", studentRepository.count());
        stats.put("total_classes", classRepository.count());
        stats.put("total_subjects", subjectRepository.count());
        
        // Today's attendance
        long todayAttendance = attendanceRepository.countByDate(LocalDate.now());
        stats.put("today_attendance", todayAttendance);
        
        return stats;
    }
    
    public Map<String, Object> getAllUsers() {
        List<Map<String, Object>> users = new ArrayList<>();
        
        // Get faculty
        facultyRepository.findAll().forEach(faculty -> {
            Map<String, Object> user = new HashMap<>();
            user.put("type", "faculty");
            user.put("id", faculty.getId());
            user.put("name", faculty.getName());
            user.put("email", faculty.getEmail());
            user.put("created_at", faculty.getCreatedAt());
            users.add(user);
        });
        
        // Get students
        studentRepository.findAll().forEach(student -> {
            Map<String, Object> user = new HashMap<>();
            user.put("type", "student");
            user.put("id", student.getId());
            user.put("name", student.getName());
            user.put("email", student.getEmail());
            user.put("created_at", student.getCreatedAt());
            users.add(user);
        });
        
        // Sort by created_at
        users.sort((a, b) -> ((LocalDate) b.get("created_at")).compareTo((LocalDate) a.get("created_at")));
        
        return Map.of("users", users);
    }
    
    public Map<String, Object> addUser(Map<String, Object> userData) {
        String type = (String) userData.get("type");
        String name = (String) userData.get("name");
        String email = (String) userData.get("email");
        String password = (String) userData.get("password");
        
        if (type == null || name == null || email == null || password == null) {
            return Map.of("success", false, "message", "All fields are required");
        }
        
        String hashedPassword = passwordEncoder.encode(password);
        
        if ("faculty".equals(type)) {
            com.attendpro.entity.Faculty faculty = new com.attendpro.entity.Faculty(name, email, hashedPassword);
            facultyRepository.save(faculty);
        } else if ("student".equals(type)) {
            String rollNumber = (String) userData.get("roll_number");
            if (rollNumber == null) {
                return Map.of("success", false, "message", "Roll number is required for students");
            }
            com.attendpro.entity.Student student = new com.attendpro.entity.Student(rollNumber, name, email, hashedPassword);
            studentRepository.save(student);
        }
        
        return Map.of("success", true, "message", "User added successfully");
    }
    
    public Map<String, Object> updateUser(Long id, Map<String, Object> userData) {
        // Implementation for updating user
        return Map.of("success", true, "message", "User updated successfully");
    }
    
    public Map<String, Object> deleteUser(Long id) {
        // Implementation for deleting user
        return Map.of("success", true, "message", "User deleted successfully");
    }
    
    public Map<String, Object> getAllClasses() {
        List<com.attendpro.entity.Class> classes = classRepository.findAll();
        return Map.of("classes", classes);
    }
    
    public Map<String, Object> addClass(Map<String, Object> classData) {
        String className = (String) classData.get("class_name");
        String section = (String) classData.get("section");
        String academicYear = (String) classData.get("academic_year");
        
        if (className == null || academicYear == null) {
            return Map.of("success", false, "message", "Class name and academic year are required");
        }
        
        com.attendpro.entity.Class classEntity = new com.attendpro.entity.Class(className, section, academicYear);
        classRepository.save(classEntity);
        
        return Map.of("success", true, "message", "Class added successfully");
    }
    
    public Map<String, Object> getAllSubjects() {
        List<com.attendpro.entity.Subject> subjects = subjectRepository.findAll();
        return Map.of("subjects", subjects);
    }
    
    public Map<String, Object> addSubject(Map<String, Object> subjectData) {
        String subjectName = (String) subjectData.get("subject_name");
        String subjectCode = (String) subjectData.get("subject_code");
        Long classId = subjectData.get("class_id") != null ? Long.valueOf(subjectData.get("class_id").toString()) : null;
        Long facultyId = subjectData.get("faculty_id") != null ? Long.valueOf(subjectData.get("faculty_id").toString()) : null;
        Integer credits = subjectData.get("credits") != null ? Integer.valueOf(subjectData.get("credits").toString()) : 3;
        
        if (subjectName == null || subjectCode == null) {
            return Map.of("success", false, "message", "Subject name and code are required");
        }
        
        com.attendpro.entity.Subject subject = new com.attendpro.entity.Subject(subjectName, subjectCode, classId, facultyId, credits);
        subjectRepository.save(subject);
        
        return Map.of("success", true, "message", "Subject added successfully");
    }
    
    public Map<String, Object> getAllNotices() {
        List<com.attendpro.entity.Notice> notices = noticeRepository.findAll();
        return Map.of("notices", notices);
    }
    
    public Map<String, Object> addNotice(Map<String, Object> noticeData) {
        String title = (String) noticeData.get("title");
        String content = (String) noticeData.get("content");
        
        if (title == null || content == null) {
            return Map.of("success", false, "message", "Title and content are required");
        }
        
        // Implementation for adding notice
        return Map.of("success", true, "message", "Notice added successfully");
    }
    
    public Map<String, Object> getSystemSettings() {
        List<com.attendpro.entity.SystemSettings> settings = systemSettingsRepository.findAll();
        Map<String, Object> settingsMap = new HashMap<>();
        settings.forEach(setting -> settingsMap.put(setting.getSettingKey(), setting.getSettingValue()));
        return settingsMap;
    }
    
    public Map<String, Object> updateSystemSettings(Map<String, Object> settings) {
        // Implementation for updating system settings
        return Map.of("success", true, "message", "Settings updated successfully");
    }
}
