package com.curso.react.shared;

import java.io.Serializable;

public class PostCreationDto implements Serializable{
    public static final long serialVersionUID=1L;

    private String title;
    private String content;
    private Long exposureId;
    private Integer expirationTime;
    private String userEmail;
    
    public PostCreationDto() {
    }

    public PostCreationDto(String title, String content, Long exposureId, Integer expirationTime, String userEmail) {
        this.title = title;
        this.content = content;
        this.exposureId = exposureId;
        this.expirationTime = expirationTime;
        this.userEmail = userEmail;
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

    public Long getExposureId() {
        return exposureId;
    }

    public void setExposureId(Long exposureId) {
        this.exposureId = exposureId;
    }

    public Integer getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Integer expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "PostCreationDto [title=" + title + ", content=" + content + ", exposureId=" + exposureId
                + ", expirationTime=" + expirationTime + ", userEmail=" + userEmail + "]";
    }
}
