package com.example.demo.service.imp;

import com.example.demo.model.Friend;
import com.example.demo.repository.FriendRepository;
import com.example.demo.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;

public class FriendServiceImp implements FriendService {
    @Autowired
    private FriendRepository friendRepository;

    @Override
    public void save(Friend friend) {
        friendRepository.save(friend);
    }
}
