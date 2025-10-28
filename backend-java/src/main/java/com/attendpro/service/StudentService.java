package com.attendpro.service;

import com.attendpro.dto.LoginRequest;
import com.attendpro.dto.LoginResponse;
import com.attendpro.entity.Student;
import com.attendpro.repository.StudentRepository;
import com.attendpro.repository.AttendanceRepository;
import com.attendpro.repository.NoticeRepository;
import com.attendpro.repository.SubjectRepository;
import com.attendpro.repository.ClassRepository;
import com.attendpro.entity.TargetAudience;
import com.attendpro.entity.NoticeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private NoticeRepository noticeRepository;
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private ClassRepository classRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<Student> studentOpt = studentRepository.findByRollNumber(loginRequest.getUsername());
        
        if (studentOpt.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), studentOpt.get().getPassword())) {
            Student student = studentOpt.get();
            LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getRollNumber(),
                "STUDENT"
            );
            LoginResponse response = new LoginResponse(true, "Login successful", "jwt-token-here", userInfo);
            response.setRedirect("../student/dashboard.html");
            return response;
        } else {
            return new LoginResponse(false, "Invalid roll number or password");
        }
    }
    
    public Map<String, Object> getStudentAttendance(Long studentId) {
        try {
            List<Object[]> attendanceSummary = attendanceRepository.getAttendanceSummaryByStudentId(studentId);
            List<Map<String, Object>> attendance = new ArrayList<>();
            
            for (Object[] row : attendanceSummary) {
                Long subjectId = (Long) row[0];
                Long totalClasses = (Long) row[1];
                Long presentCount = (Long) row[2];
                
                // Get subject details (you might need to inject SubjectRepository)
                double percentage = totalClasses > 0 ? (presentCount * 100.0) / totalClasses : 0;
                
                Map<String, Object> subjectAttendance = new HashMap<>();
                subjectAttendance.put("subject_id", subjectId);
                subjectAttendance.put("subject_name", "Subject Name"); // Get from subject repository
                subjectAttendance.put("subject_code", "SUB001"); // Get from subject repository
                subjectAttendance.put("total_classes", totalClasses);
                subjectAttendance.put("present_count", presentCount);
                subjectAttendance.put("percentage", Math.round(percentage * 100.0) / 100.0);
                
                attendance.add(subjectAttendance);
            }
            
            return Map.of("attendance", attendance);
            
        } catch (Exception e) {
            return Map.of("error", "Database error: " + e.getMessage());
        }
    }
    
    public Map<String, Object> getNoticesForStudent(Long classId) {
        try {
            List<com.attendpro.entity.Notice> notices;
            
            if (classId != null) {
                // Get notices for specific class
                notices = noticeRepository.findPublishedByTargetClassId(classId);
            } else {
                // Get all published notices
                notices = noticeRepository.findAllPublished();
            }
            
            // Also get notices for all students
            List<com.attendpro.entity.Notice> allStudentNotices = noticeRepository.findPublishedByTargetAudience(TargetAudience.STUDENTS);
            notices.addAll(allStudentNotices);
            
            // Remove duplicates
            Set<Long> noticeIds = new HashSet<>();
            List<com.attendpro.entity.Notice> uniqueNotices = new ArrayList<>();
            for (com.attendpro.entity.Notice notice : notices) {
                if (!noticeIds.contains(notice.getId())) {
                    noticeIds.add(notice.getId());
                    uniqueNotices.add(notice);
                }
            }
            
            return Map.of("notices", uniqueNotices);
            
        } catch (Exception e) {
            return Map.of("error", "Database error: " + e.getMessage());
        }
    }
    
    public Map<String, Object> getStudentProfile(Long studentId) {
        try {
            Optional<Student> studentOpt = studentRepository.findById(studentId);
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                Map<String, Object> profile = new HashMap<>();
                profile.put("id", student.getId());
                profile.put("name", student.getName());
                profile.put("email", student.getEmail());
                profile.put("rollNumber", student.getRollNumber());
                profile.put("department", student.getDepartment());
                profile.put("semester", student.getSemester());
                profile.put("phone", student.getPhone());
                profile.put("classId", student.getClassId());
                profile.put("createdAt", student.getCreatedAt());
                return profile;
            } else {
                return Map.of("error", "Student not found");
            }
        } catch (Exception e) {
            return Map.of("error", "Database error: " + e.getMessage());
        }
    }
    
    public Map<String, Object> getStudentGrades(Long studentId) {
        try {
            // Mock grades data - in real implementation, this would come from a grades table
            List<Map<String, Object>> grades = new ArrayList<>();
            
            Map<String, Object> mathGrade = new HashMap<>();
            mathGrade.put("subject", "Mathematics");
            mathGrade.put("subjectCode", "MATH101");
            mathGrade.put("grade", "A+");
            mathGrade.put("marks", 95);
            mathGrade.put("maxMarks", 100);
            mathGrade.put("percentage", 95.0);
            grades.add(mathGrade);
            
            Map<String, Object> physicsGrade = new HashMap<>();
            physicsGrade.put("subject", "Physics");
            physicsGrade.put("subjectCode", "PHY101");
            physicsGrade.put("grade", "A");
            physicsGrade.put("marks", 88);
            physicsGrade.put("maxMarks", 100);
            physicsGrade.put("percentage", 88.0);
            grades.add(physicsGrade);
            
            Map<String, Object> chemistryGrade = new HashMap<>();
            chemistryGrade.put("subject", "Chemistry");
            chemistryGrade.put("subjectCode", "CHEM101");
            chemistryGrade.put("grade", "A-");
            chemistryGrade.put("marks", 85);
            chemistryGrade.put("maxMarks", 100);
            chemistryGrade.put("percentage", 85.0);
            grades.add(chemistryGrade);
            
            double overallPercentage = grades.stream()
                .mapToDouble(grade -> (Double) grade.get("percentage"))
                .average()
                .orElse(0.0);
            
            String overallGrade = getGradeFromPercentage(overallPercentage);
            
            Map<String, Object> result = new HashMap<>();
            result.put("grades", grades);
            result.put("overallPercentage", Math.round(overallPercentage * 100.0) / 100.0);
            result.put("overallGrade", overallGrade);
            result.put("totalSubjects", grades.size());
            
            return result;
        } catch (Exception e) {
            return Map.of("error", "Database error: " + e.getMessage());
        }
    }
    
    private String getGradeFromPercentage(double percentage) {
        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B+";
        if (percentage >= 60) return "B";
        if (percentage >= 50) return "C";
        return "F";
    }
    
    public Map<String, Object> getStudentSchedule(Long studentId) {
        try {
            // Mock schedule data - in real implementation, this would come from a schedule table
            List<Map<String, Object>> schedule = new ArrayList<>();
            
            // Monday
            schedule.add(createScheduleEntry("Monday", "09:00", "10:30", "Mathematics", "Room 101", "Dr. Smith"));
            schedule.add(createScheduleEntry("Monday", "11:00", "12:30", "Physics", "Room 102", "Dr. Johnson"));
            schedule.add(createScheduleEntry("Monday", "14:00", "15:30", "Chemistry", "Lab 1", "Dr. Brown"));
            
            // Tuesday
            schedule.add(createScheduleEntry("Tuesday", "09:00", "10:30", "English", "Room 103", "Dr. Davis"));
            schedule.add(createScheduleEntry("Tuesday", "11:00", "12:30", "Mathematics", "Room 101", "Dr. Smith"));
            schedule.add(createScheduleEntry("Tuesday", "14:00", "15:30", "Computer Science", "Lab 2", "Dr. Wilson"));
            
            // Wednesday
            schedule.add(createScheduleEntry("Wednesday", "09:00", "10:30", "Physics", "Room 102", "Dr. Johnson"));
            schedule.add(createScheduleEntry("Wednesday", "11:00", "12:30", "Chemistry", "Lab 1", "Dr. Brown"));
            schedule.add(createScheduleEntry("Wednesday", "14:00", "15:30", "Mathematics", "Room 101", "Dr. Smith"));
            
            // Thursday
            schedule.add(createScheduleEntry("Thursday", "09:00", "10:30", "English", "Room 103", "Dr. Davis"));
            schedule.add(createScheduleEntry("Thursday", "11:00", "12:30", "Computer Science", "Lab 2", "Dr. Wilson"));
            schedule.add(createScheduleEntry("Thursday", "14:00", "15:30", "Physics", "Room 102", "Dr. Johnson"));
            
            // Friday
            schedule.add(createScheduleEntry("Friday", "09:00", "10:30", "Chemistry", "Lab 1", "Dr. Brown"));
            schedule.add(createScheduleEntry("Friday", "11:00", "12:30", "Mathematics", "Room 101", "Dr. Smith"));
            schedule.add(createScheduleEntry("Friday", "14:00", "15:30", "English", "Room 103", "Dr. Davis"));
            
            return Map.of("schedule", schedule);
        } catch (Exception e) {
            return Map.of("error", "Database error: " + e.getMessage());
        }
    }
    
    private Map<String, Object> createScheduleEntry(String day, String startTime, String endTime, 
                                                   String subject, String room, String instructor) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("day", day);
        entry.put("startTime", startTime);
        entry.put("endTime", endTime);
        entry.put("subject", subject);
        entry.put("room", room);
        entry.put("instructor", instructor);
        return entry;
    }
    
    public Map<String, Object> getFacultyNotes(Long studentId) {
        try {
            // Mock faculty notes data - in real implementation, this would come from a notes table
            List<Map<String, Object>> notes = new ArrayList<>();
            
            Map<String, Object> note1 = new HashMap<>();
            note1.put("id", 1L);
            note1.put("title", "Mathematics - Calculus Chapter 5");
            note1.put("content", "Important formulas and examples for integration techniques. Focus on substitution method and integration by parts.");
            note1.put("subject", "Mathematics");
            note1.put("facultyName", "Dr. Smith");
            note1.put("createdAt", "2024-01-15T10:30:00");
            note1.put("fileUrl", "/files/calculus-chapter5.pdf");
            notes.add(note1);
            
            Map<String, Object> note2 = new HashMap<>();
            note2.put("id", 2L);
            note2.put("title", "Physics - Thermodynamics");
            note2.put("content", "Laws of thermodynamics with practical examples. Study the Carnot cycle and entropy concepts.");
            note2.put("subject", "Physics");
            note2.put("facultyName", "Dr. Johnson");
            note2.put("createdAt", "2024-01-14T14:20:00");
            note2.put("fileUrl", "/files/thermodynamics-notes.pdf");
            notes.add(note2);
            
            Map<String, Object> note3 = new HashMap<>();
            note3.put("id", 3L);
            note3.put("title", "Chemistry - Organic Reactions");
            note3.put("content", "Key organic reactions and mechanisms. Practice with reaction pathways and stereochemistry.");
            note3.put("subject", "Chemistry");
            note3.put("facultyName", "Dr. Brown");
            note3.put("createdAt", "2024-01-13T09:15:00");
            note3.put("fileUrl", "/files/organic-reactions.pdf");
            notes.add(note3);
            
            return Map.of("notes", notes);
        } catch (Exception e) {
            return Map.of("error", "Database error: " + e.getMessage());
        }
    }
    
    public Map<String, Object> getDashboardStats(Long studentId) {
        try {
            // Get attendance summary
            List<Object[]> attendanceSummary = attendanceRepository.getAttendanceSummaryByStudentId(studentId);
            double totalAttendancePercentage = 0.0;
            int totalSubjects = 0;
            
            if (!attendanceSummary.isEmpty()) {
                for (Object[] row : attendanceSummary) {
                    Long totalClasses = (Long) row[1];
                    Long presentCount = (Long) row[2];
                    if (totalClasses > 0) {
                        totalAttendancePercentage += (presentCount * 100.0) / totalClasses;
                        totalSubjects++;
                    }
                }
                if (totalSubjects > 0) {
                    totalAttendancePercentage = totalAttendancePercentage / totalSubjects;
                }
            }
            
            // Mock additional stats
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalAttendancePercentage", Math.round(totalAttendancePercentage * 100.0) / 100.0);
            stats.put("monthlyAttendancePercentage", Math.min(100.0, totalAttendancePercentage + Math.random() * 10));
            stats.put("totalSubjects", totalSubjects);
            stats.put("upcomingAssignments", 3);
            stats.put("completedAssignments", 12);
            stats.put("studyStreak", 7);
            stats.put("totalStudyHours", 45);
            stats.put("achievementsEarned", 8);
            stats.put("totalAchievements", 15);
            
            return stats;
        } catch (Exception e) {
            return Map.of("error", "Database error: " + e.getMessage());
        }
    }
}
