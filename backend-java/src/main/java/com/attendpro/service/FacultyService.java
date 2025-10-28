package com.attendpro.service;

import com.attendpro.dto.LoginRequest;
import com.attendpro.dto.LoginResponse;
import com.attendpro.entity.Faculty;
import com.attendpro.entity.Attendance;
import com.attendpro.entity.AttendanceStatus;
import com.attendpro.repository.FacultyRepository;
import com.attendpro.repository.SubjectRepository;
import com.attendpro.repository.StudentRepository;
import com.attendpro.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class FacultyService {
    
    @Autowired
    private FacultyRepository facultyRepository;
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<Faculty> facultyOpt = facultyRepository.findByUsername(loginRequest.getUsername());
        
        if (facultyOpt.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), facultyOpt.get().getPassword())) {
            Faculty faculty = facultyOpt.get();
            LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
                faculty.getId(),
                faculty.getName(),
                faculty.getEmail(),
                faculty.getUsername(),
                "FACULTY"
            );
            LoginResponse response = new LoginResponse(true, "Login successful", "jwt-token-here", userInfo);
            response.setRedirect("../faculty/dashboard.html");
            return response;
        } else {
            return new LoginResponse(false, "Invalid username or password");
        }
    }
    
    public Map<String, Object> getSubjectsByFaculty(Long facultyId) {
        List<com.attendpro.entity.Subject> subjects = subjectRepository.findByFacultyId(facultyId);
        return Map.of("subjects", subjects);
    }
    
    public Map<String, Object> getStudentsBySubject(Long subjectId) {
        Optional<com.attendpro.entity.Subject> subjectOpt = subjectRepository.findById(subjectId);
        if (subjectOpt.isEmpty()) {
            return Map.of("error", "Subject not found");
        }
        
        com.attendpro.entity.Subject subject = subjectOpt.get();
        List<com.attendpro.entity.Student> students = studentRepository.findByDepartmentAndSemester(
            subject.getClassEntity().getClassName(), // Assuming department is stored in class name
            "1" // Default semester, should be configured properly
        );
        
        return Map.of("students", students);
    }
    
    @Transactional
    public Map<String, Object> saveAttendance(Map<String, Object> attendanceData) {
        try {
            Long subjectId = Long.valueOf(attendanceData.get("subject_id").toString());
            String dateStr = (String) attendanceData.get("date");
            LocalDate date = LocalDate.parse(dateStr);
            Long facultyId = Long.valueOf(attendanceData.get("faculty_id").toString());
            
            @SuppressWarnings("unchecked")
            Map<String, String> attendanceMap = (Map<String, String>) attendanceData.get("attendance");
            
            // Delete existing attendance for this date and subject
            List<Attendance> existingAttendance = attendanceRepository.findBySubjectIdAndDate(subjectId, date);
            attendanceRepository.deleteAll(existingAttendance);
            
            // Insert new attendance records
            for (Map.Entry<String, String> entry : attendanceMap.entrySet()) {
                Long studentId = Long.valueOf(entry.getKey());
                AttendanceStatus status = AttendanceStatus.valueOf(entry.getValue().toUpperCase());
                
                Attendance attendance = new Attendance(studentId, subjectId, date, status, facultyId);
                attendanceRepository.save(attendance);
            }
            
            return Map.of("success", true, "message", "Attendance saved successfully");
            
        } catch (Exception e) {
            return Map.of("success", false, "message", "Failed to save attendance: " + e.getMessage());
        }
    }
    
    public Map<String, Object> getAttendanceReport(Long subjectId) {
        try {
            List<Object[]> attendanceSummary = attendanceRepository.getAttendanceSummaryBySubjectId(subjectId);
            List<Map<String, Object>> report = new ArrayList<>();
            
            for (Object[] row : attendanceSummary) {
                Long studentId = (Long) row[0];
                Long totalClasses = (Long) row[1];
                Long presentCount = (Long) row[2];
                
                Optional<com.attendpro.entity.Student> studentOpt = studentRepository.findById(studentId);
                if (studentOpt.isPresent()) {
                    com.attendpro.entity.Student student = studentOpt.get();
                    double percentage = totalClasses > 0 ? (presentCount * 100.0) / totalClasses : 0;
                    
                    Map<String, Object> studentReport = new HashMap<>();
                    studentReport.put("roll_number", student.getRollNumber());
                    studentReport.put("name", student.getName());
                    studentReport.put("total_classes", totalClasses);
                    studentReport.put("present_count", presentCount);
                    studentReport.put("percentage", Math.round(percentage * 100.0) / 100.0);
                    
                    report.add(studentReport);
                }
            }
            
            return Map.of("report", report);
            
        } catch (Exception e) {
            return Map.of("error", "Database error: " + e.getMessage());
        }
    }
}
