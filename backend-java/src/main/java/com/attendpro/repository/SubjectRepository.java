package com.attendpro.repository;

import com.attendpro.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    
    Optional<Subject> findBySubjectCode(String subjectCode);
    
    List<Subject> findByClassId(Long classId);
    
    List<Subject> findByFacultyId(Long facultyId);
    
    List<Subject> findByClassIdAndFacultyId(Long classId, Long facultyId);
    
    @Query("SELECT s FROM Subject s WHERE s.subjectName LIKE %:subjectName% OR s.subjectCode LIKE %:subjectCode%")
    List<Subject> findBySubjectNameOrSubjectCodeContaining(@Param("subjectName") String subjectName, @Param("subjectCode") String subjectCode);
    
    @Query("SELECT s FROM Subject s WHERE s.classId = :classId AND (s.subjectName LIKE %:search% OR s.subjectCode LIKE %:search%)")
    List<Subject> findByClassIdAndSubjectNameOrSubjectCodeContaining(@Param("classId") Long classId, @Param("search") String search);
    
    @Query("SELECT s FROM Subject s WHERE s.facultyId = :facultyId AND (s.subjectName LIKE %:search% OR s.subjectCode LIKE %:search%)")
    List<Subject> findByFacultyIdAndSubjectNameOrSubjectCodeContaining(@Param("facultyId") Long facultyId, @Param("search") String search);
    
    boolean existsBySubjectCode(String subjectCode);
    
    long countByClassId(Long classId);
    
    long countByFacultyId(Long facultyId);
}
