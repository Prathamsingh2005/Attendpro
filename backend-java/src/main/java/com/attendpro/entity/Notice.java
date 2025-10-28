package com.attendpro.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notices")
public class Notice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "target_audience", nullable = false)
    private TargetAudience targetAudience = TargetAudience.ALL;
    
    @Column(name = "target_class_id")
    private Long targetClassId;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticePriority priority = NoticePriority.MEDIUM;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticeStatus status = NoticeStatus.DRAFT;
    
    @Column(name = "created_by", nullable = false)
    private Long createdBy;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    // Many-to-one relationship with class
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_class_id", insertable = false, updatable = false)
    private Class targetClass;
    
    // Many-to-one relationship with admin (created by)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private Admin createdByAdmin;
    
    // Constructors
    public Notice() {}
    
    public Notice(String title, String content, TargetAudience targetAudience, Long targetClassId, 
                  NoticePriority priority, NoticeStatus status, Long createdBy) {
        this.title = title;
        this.content = content;
        this.targetAudience = targetAudience;
        this.targetClassId = targetClassId;
        this.priority = priority;
        this.status = status;
        this.createdBy = createdBy;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public TargetAudience getTargetAudience() {
        return targetAudience;
    }
    
    public void setTargetAudience(TargetAudience targetAudience) {
        this.targetAudience = targetAudience;
    }
    
    public Long getTargetClassId() {
        return targetClassId;
    }
    
    public void setTargetClassId(Long targetClassId) {
        this.targetClassId = targetClassId;
    }
    
    public NoticePriority getPriority() {
        return priority;
    }
    
    public void setPriority(NoticePriority priority) {
        this.priority = priority;
    }
    
    public NoticeStatus getStatus() {
        return status;
    }
    
    public void setStatus(NoticeStatus status) {
        this.status = status;
    }
    
    public Long getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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
    
    public Class getTargetClass() {
        return targetClass;
    }
    
    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
    }
    
    public Admin getCreatedByAdmin() {
        return createdByAdmin;
    }
    
    public void setCreatedByAdmin(Admin createdByAdmin) {
        this.createdByAdmin = createdByAdmin;
    }
}
