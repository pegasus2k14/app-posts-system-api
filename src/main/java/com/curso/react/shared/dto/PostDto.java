package com.curso.react.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class PostDto implements Serializable{
    
    public static final long serialVersionUID=1L;

    private Long id;
    private String postId;
    private String title;
    private String content;
    private Date expiresAt; 
    private Date createdAt;

    private UserDto user;

    private ExposureDto exposure;

    public PostDto() {
    }


    public PostDto(Long id, String postId, String title, String content, Date expiresAt, Date createdAt, UserDto user,
            ExposureDto exposure) {
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


    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public ExposureDto getExposure() {
        return exposure;
    }

    public void setExposure(ExposureDto exposure) {
        this.exposure = exposure;
    }
    
    @Override
    public String toString() {
        return "PostDto [id=" + id + ", postId=" + postId + ", title=" + title + ", content=" + content + ", expiresAt="
                + expiresAt + ", createdAt=" + createdAt + ", user=" + user + ", exposure=" + exposure + "]";
    }
}
