package com.curso.react.model.request;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PostCreateRequestModel {
    @NotEmpty(message = "El titulo es obligatorio")
    private String title;
    @NotEmpty(message = "El contenido es obligatorio")
    private String content;
    @NotNull(message = "La exposicion del post es obligatoria")
    @Range(min = 1, max = 2, message = "La exposicion del post es invalida")
    private Long exposureId;
    @NotNull(message = "El tiempo de expiracion del post es obligatorio")
    @Range(min = 0, max = 1440, message = "El tiempo de expiración es invalido")
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
