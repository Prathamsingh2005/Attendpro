package com.attendpro.repository;

import com.attendpro.entity.Notice;
import com.attendpro.entity.NoticeStatus;
import com.attendpro.entity.TargetAudience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    
    List<Notice> findByStatus(NoticeStatus status);
    
    List<Notice> findByTargetAudience(TargetAudience targetAudience);
    
    List<Notice> findByTargetClassId(Long targetClassId);
    
    List<Notice> findByCreatedBy(Long createdBy);
    
    List<Notice> findByStatusAndTargetAudience(NoticeStatus status, TargetAudience targetAudience);
    
    @Query("SELECT n FROM Notice n WHERE n.title LIKE %:title% OR n.content LIKE %:content%")
    List<Notice> findByTitleOrContentContaining(@Param("title") String title, @Param("content") String content);
    
    @Query("SELECT n FROM Notice n WHERE n.status = :status AND (n.title LIKE %:search% OR n.content LIKE %:search%)")
    List<Notice> findByStatusAndTitleOrContentContaining(@Param("status") NoticeStatus status, @Param("search") String search);
    
    @Query("SELECT n FROM Notice n WHERE n.targetAudience = :targetAudience AND n.status = 'PUBLISHED' ORDER BY n.createdAt DESC")
    List<Notice> findPublishedByTargetAudience(@Param("targetAudience") TargetAudience targetAudience);
    
    @Query("SELECT n FROM Notice n WHERE n.targetClassId = :targetClassId AND n.status = 'PUBLISHED' ORDER BY n.createdAt DESC")
    List<Notice> findPublishedByTargetClassId(@Param("targetClassId") Long targetClassId);
    
    @Query("SELECT n FROM Notice n WHERE n.status = 'PUBLISHED' ORDER BY n.createdAt DESC")
    List<Notice> findAllPublished();
    
    long countByStatus(NoticeStatus status);
    
    long countByTargetAudience(TargetAudience targetAudience);
    
    long countByCreatedBy(Long createdBy);
}
