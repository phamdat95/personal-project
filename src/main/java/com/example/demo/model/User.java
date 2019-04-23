package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userName;
    private String passWord;
    private String address;
    private String phoneNumber;
    private String email;
    private String sex;
    private String firstName;
    private String lastName;
    private String role;

    @JsonIgnoreProperties(value = {"user", "image"}, allowSetters = true)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user",  orphanRemoval = true)
    private List<Comment> comments;

    @JsonIgnoreProperties(value = {"user"}, allowSetters = true)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user",  orphanRemoval = true)
    private List<Image> images;

    @JsonIgnoreProperties(value = {"user"}, allowSetters = true)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user",  orphanRemoval = true)
    private List<LikeImage> likeImages;

    @JsonIgnoreProperties(value = {"user"}, allowSetters = true)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user",  orphanRemoval = true)
    private List<Friend> friends;

    public User() {
    }

    public User(String userName, String passWord, String address, String phoneNumber, String email, String sex, String firstName, String lastName, String role, List<Comment> comments, List<Image> images, List<LikeImage> likeImages, List<Friend> friends) {
        this.userName = userName;
        this.passWord = passWord;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.sex = sex;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.comments = comments;
        this.images = images;
        this.likeImages = likeImages;
        this.friends = friends;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<LikeImage> getLikeImages() {
        return likeImages;
    }

    public void setLikeImages(List<LikeImage> likeImages) {
        this.likeImages = likeImages;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
