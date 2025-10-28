package com.attendpro.repository;

import com.attendpro.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    
    Optional<Faculty> findByEmail(String email);
    
    Optional<Faculty> findByUsername(String username);
    
    List<Faculty> findByDepartment(String department);
    
    @Query("SELECT f FROM Faculty f WHERE f.name LIKE %:name% OR f.email LIKE %:email%")
    List<Faculty> findByNameOrEmailContaining(@Param("name") String name, @Param("email") String email);
    
    boolean existsByEmail(String email);
    
    boolean existsByUsername(String username);
    
    long countByDepartment(String department);
}
