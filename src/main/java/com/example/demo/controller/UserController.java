package com.example.demo.controller;

import com.example.demo.configToken.JwtService;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.service.imp.UserServiceImp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @RequestMapping(method = RequestMethod.POST, value = "/resign")
    public ResponseEntity<User> createUser (@RequestBody User user){
        userService.save(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<String> login (@RequestBody User user){
        List<User> users = userService.getList();
        String result = "";
        for (User item: users){
            if (StringUtils.equals(user.getUserName(),item.getUserName()) &&
            StringUtils.equals(user.getPassWord(), item.getPassWord())){
                result = jwtService.generateTokenLogin(item.getUserName(), item.getRole());
            }
        }
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.PATCH, value = "/user/update")
    public ResponseEntity<User> updateUser (@RequestBody User user, HttpServletRequest request){
        String userName = jwtService.getUsernameFromToken(request.getHeader("authorization"));
        User user1 = userService.findUserName(userName);
        if (user1 == null) {
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
        user1.setUserName(user.getUserName());
        user1.setAddress(user.getAddress());
        user1.setPassWord(user.getPassWord());
        user1.setEmail(user.getEmail());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        userService.save(user1);
        return new ResponseEntity<User>(user1, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public ResponseEntity<List<String>> Logout(HttpServletRequest request) {
        String token = request.getHeader("authorization");
        UserServiceImp.blackList.add(token);
        return new ResponseEntity<List<String>>(UserServiceImp.blackList, HttpStatus.OK);
    }
}
