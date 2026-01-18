package com.curso.react.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "exposures")
public class ExposureEntity implements Serializable{

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String type;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "exposure")
    private List<PostEntity> posts = new ArrayList<>();

    public ExposureEntity() {
    }

    public ExposureEntity(Long id, String type, List<PostEntity> posts) {
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

    public List<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(List<PostEntity> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "ExposureEntity [id=" + id + ", type=" + type + ", posts=" + posts + "]";
    } 
}
