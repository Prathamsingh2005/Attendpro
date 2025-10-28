package com.attendpro.repository;

import com.attendpro.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Optional<Student> findByRollNumber(String rollNumber);
    
    Optional<Student> findByEmail(String email);
    
    List<Student> findByClassId(Long classId);
    
    List<Student> findByDepartment(String department);
    
    List<Student> findBySemester(String semester);
    
    List<Student> findByDepartmentAndSemester(String department, String semester);
    
    @Query("SELECT s FROM Student s WHERE s.name LIKE %:name% OR s.email LIKE %:email% OR s.rollNumber LIKE %:rollNumber%")
    List<Student> findByNameOrEmailOrRollNumberContaining(@Param("name") String name, @Param("email") String email, @Param("rollNumber") String rollNumber);
    
    @Query("SELECT s FROM Student s WHERE s.department = :department AND s.semester = :semester AND (s.name LIKE %:search% OR s.rollNumber LIKE %:search%)")
    List<Student> findByDepartmentAndSemesterAndNameOrRollNumberContaining(@Param("department") String department, @Param("semester") String semester, @Param("search") String search);
    
    boolean existsByRollNumber(String rollNumber);
    
    boolean existsByEmail(String email);
    
    long countByClassId(Long classId);
    
    long countByDepartment(String department);
    
    long countByDepartmentAndSemester(String department, String semester);
}
