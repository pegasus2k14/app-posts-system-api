package com.curso.react.shared.dto;

import java.io.Serializable;
import java.util.List;

public class ExposureDto implements Serializable{
    public static final long serialVersionUID=1L;

    private Long id;

    private String type;

    List<ExposureDto> posts;

    public ExposureDto() {
    }

    public ExposureDto(Long id, String type, List<ExposureDto> posts) {
        this.id = id;
        this.type = type;
        this.posts = posts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ExposureDto> getPosts() {
        return posts;
    }

    public void setPosts(List<ExposureDto> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "ExposureDto [id=" + id + ", type=" + type + ", posts=" + posts + "]";
    }
}
