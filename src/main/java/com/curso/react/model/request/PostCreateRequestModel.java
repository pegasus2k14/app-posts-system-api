package com.curso.react.model.request;

public class PostCreateRequestModel {
    private String title;
    private String content;
    private Long exposureId;
    private Integer expirationTime;
    
    public PostCreateRequestModel() {
    }

    public PostCreateRequestModel(String title, String content, Long exposureId, Integer expirationTime) {
        this.title = title;
        this.content = content;
        this.exposureId = exposureId;
        this.expirationTime = expirationTime;
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

    @Override
    public String toString() {
        return "PostCreateRequestModel [title=" + title + ", content=" + content + ", exposureId=" + exposureId
                + ", expirationTime=" + expirationTime + "]";
    }
}
