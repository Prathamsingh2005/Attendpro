package com.attendpro.repository;

import com.attendpro.entity.User;
import com.attendpro.entity.UserRole;
import com.attendpro.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    List<User> findByRole(UserRole role);
    
    List<User> findByStatus(UserStatus status);
    
    List<User> findByRoleAndStatus(UserRole role, UserStatus status);
    
    @Query("SELECT u FROM User u WHERE u.name LIKE %:name% OR u.email LIKE %:email%")
    List<User> findByNameOrEmailContaining(@Param("name") String name, @Param("email") String email);
    
    @Query("SELECT u FROM User u WHERE u.role = :role AND (u.name LIKE %:search% OR u.email LIKE %:search%)")
    List<User> findByRoleAndNameOrEmailContaining(@Param("role") UserRole role, @Param("search") String search);
    
    boolean existsByEmail(String email);
    
    long countByRole(UserRole role);
    
    long countByStatus(UserStatus status);
}
