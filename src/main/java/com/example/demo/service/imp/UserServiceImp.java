package com.example.demo.service.imp;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImp implements UserService {
    public static List<String> blackList = new ArrayList<String>();

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUserName(String name) {
        List<User> users = getList();
        return users.stream().filter(i -> name.equals(i.getUserName())).findAny().orElse(null);
    }

    @Override
    public List<User> getList() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }
}
