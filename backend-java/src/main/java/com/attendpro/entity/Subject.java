package com.attendpro.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "subject_name", nullable = false, length = 100)
    private String subjectName;
    
    @Column(name = "subject_code", nullable = false, unique = true, length = 20)
    private String subjectCode;
    
    @Column(name = "class_id")
    private Long classId;
    
    @Column(name = "faculty_id")
    private Long facultyId;
    
    @Column
    private Integer credits = 3;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    // Many-to-one relationship with class
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", insertable = false, updatable = false)
    private Class classEntity;
    
    // Many-to-one relationship with faculty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", insertable = false, updatable = false)
    private Faculty faculty;
    
    // One-to-many relationship with attendance
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attendance> attendanceRecords;
    
    // Constructors
    public Subject() {}
    
    public Subject(String subjectName, String subjectCode, Long classId, Long facultyId, Integer credits) {
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.classId = classId;
        this.facultyId = facultyId;
        this.credits = credits;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getSubjectName() {
        return subjectName;
    }
    
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
    public String getSubjectCode() {
        return subjectCode;
    }
    
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
    
    public Long getClassId() {
        return classId;
    }
    
    public void setClassId(Long classId) {
        this.classId = classId;
    }
    
    public Long getFacultyId() {
        return facultyId;
    }
    
    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }
    
    public Integer getCredits() {
        return credits;
    }
    
    public void setCredits(Integer credits) {
        this.credits = credits;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Class getClassEntity() {
        return classEntity;
    }
    
    public void setClassEntity(Class classEntity) {
        this.classEntity = classEntity;
    }
    
    public Faculty getFaculty() {
        return faculty;
    }
    
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
    
    public List<Attendance> getAttendanceRecords() {
        return attendanceRecords;
    }
    
    public void setAttendanceRecords(List<Attendance> attendanceRecords) {
        this.attendanceRecords = attendanceRecords;
    }
}
