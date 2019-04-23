package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

public interface UserService {
    void save(User user);

    User findUserName (String name);

    List<User> getList();

    User findById (Long id);

}
