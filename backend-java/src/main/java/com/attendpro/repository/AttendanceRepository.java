package com.attendpro.repository;

import com.attendpro.entity.Attendance;
import com.attendpro.entity.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    
    List<Attendance> findByStudentId(Long studentId);
    
    List<Attendance> findBySubjectId(Long subjectId);
    
    List<Attendance> findByDate(LocalDate date);
    
    List<Attendance> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
    
    List<Attendance> findBySubjectIdAndDate(Long subjectId, LocalDate date);
    
    List<Attendance> findByStudentIdAndDate(Long studentId, LocalDate date);
    
    List<Attendance> findByStatus(AttendanceStatus status);
    
    List<Attendance> findByMarkedBy(Long markedBy);
    
    @Query("SELECT a FROM Attendance a WHERE a.subjectId = :subjectId AND a.date BETWEEN :startDate AND :endDate")
    List<Attendance> findBySubjectIdAndDateBetween(@Param("subjectId") Long subjectId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT a FROM Attendance a WHERE a.studentId = :studentId AND a.date BETWEEN :startDate AND :endDate")
    List<Attendance> findByStudentIdAndDateBetween(@Param("studentId") Long studentId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.studentId = :studentId AND a.subjectId = :subjectId")
    long countByStudentIdAndSubjectId(@Param("studentId") Long studentId, @Param("subjectId") Long subjectId);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.studentId = :studentId AND a.subjectId = :subjectId AND a.status = :status")
    long countByStudentIdAndSubjectIdAndStatus(@Param("studentId") Long studentId, @Param("subjectId") Long subjectId, @Param("status") AttendanceStatus status);
    
    @Query("SELECT a.studentId, COUNT(a) as totalClasses, SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) as presentCount " +
           "FROM Attendance a WHERE a.subjectId = :subjectId GROUP BY a.studentId")
    List<Object[]> getAttendanceSummaryBySubjectId(@Param("subjectId") Long subjectId);
    
    @Query("SELECT a.subjectId, COUNT(a) as totalClasses, SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) as presentCount " +
           "FROM Attendance a WHERE a.studentId = :studentId GROUP BY a.subjectId")
    List<Object[]> getAttendanceSummaryByStudentId(@Param("studentId") Long studentId);
    
    boolean existsByStudentIdAndSubjectIdAndDate(Long studentId, Long subjectId, LocalDate date);
    
    long countBySubjectIdAndDate(Long subjectId, LocalDate date);
    
    long countByStudentIdAndDate(Long studentId, LocalDate date);
    
    long countByDate(LocalDate date);
}
