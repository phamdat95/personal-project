package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String imageName;

    @JsonIgnoreProperties(value = {"image", "user"}, allowSetters = true)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "image",  orphanRemoval = true)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties(value = "images", allowSetters = true)
    private User user;

    public Image() {
    }

    public Image(String imageName) {
        this.imageName = imageName;
    }

    public Image(String imageName, List<Comment> comments, User user) {
        this.imageName = imageName;
        this.comments = comments;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
