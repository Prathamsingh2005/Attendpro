package com.attendpro.repository;

import com.attendpro.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    
    List<Class> findByAcademicYear(String academicYear);
    
    List<Class> findByClassNameAndSection(String className, String section);
    
    @Query("SELECT c FROM Class c WHERE c.className LIKE %:className% OR c.section LIKE %:section%")
    List<Class> findByClassNameOrSectionContaining(@Param("className") String className, @Param("section") String section);
    
    @Query("SELECT c FROM Class c WHERE c.academicYear = :academicYear AND (c.className LIKE %:search% OR c.section LIKE %:search%)")
    List<Class> findByAcademicYearAndClassNameOrSectionContaining(@Param("academicYear") String academicYear, @Param("search") String search);
    
    boolean existsByClassNameAndSectionAndAcademicYear(String className, String section, String academicYear);
    
    long countByAcademicYear(String academicYear);
}
