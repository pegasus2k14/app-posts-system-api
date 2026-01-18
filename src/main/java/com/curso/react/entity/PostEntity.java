package com.curso.react.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class) //para q se pueda generar la fecha automatica
@Table(name = "posts")
public class PostEntity implements Serializable{

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "post_id" ,nullable = false)
    private String postId;
    @Column(nullable = false, length = 255)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(name = "expires_at",nullable = false)
    private Date expiresAt;
    @Column(name = "created_at")
    @CreatedDate  //para q se genere la fecha de forma automatica
    private Date createdAt;
    @ManyToOne
    //indicamos la columna donde se almacenara la referencia a User
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "exposure_id")
    private ExposureEntity exposure;
    
    public PostEntity() {
    }

    
    public PostEntity(Long id, String postId, String title, String content, Date expiresAt, Date createdAt,
            UserEntity user, ExposureEntity exposure) {
        this.id = id;
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.user = user;
        this.exposure = exposure;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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

   
   
    public Date getExpiresAt() {
        return expiresAt;
    }


    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }


    public Date getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    

    public ExposureEntity getExposure() {
        return exposure;
    }

    public void setExposure(ExposureEntity exposure) {
        this.exposure = exposure;
    }


    @Override
    public String toString() {
        return "PostEntity [id=" + id + ", postId=" + postId + ", title=" + title + ", content=" + content
                + ", expiresAt=" + expiresAt + ", createdAt=" + createdAt + ", user=" + user + ", exposure=" + exposure
                + "]";
    }
}
