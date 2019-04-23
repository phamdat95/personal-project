package com.example.demo.controller;

import com.example.demo.configToken.JwtService;
import com.example.demo.model.Friend;
import com.example.demo.model.User;
import com.example.demo.service.FriendService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FriendController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private FriendService friendService;

    @RequestMapping(method = RequestMethod.POST, value = "/user/{id}")
    public ResponseEntity<String> addFriend (HttpServletRequest request, @PathVariable("id") Long id) {
        User user = userService.findById(id);
        String userName = jwtService.getUsernameFromToken(request.getHeader("authorization"));
        User user1 = userService.findUserName(userName);
        Friend friend = new Friend(user.getUserName(), user1);
        friendService.save(friend);
        return new ResponseEntity<String>("add success", HttpStatus.OK);
    }
}
