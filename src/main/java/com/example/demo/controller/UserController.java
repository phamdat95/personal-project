package com.example.demo.controller;

import com.example.demo.FbComponent.RestFB;
import com.example.demo.configToken.JwtService;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RestFB restFb;

    @RequestMapping(method = RequestMethod.POST, value = "/resign")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        User userCurrent = getUser(user);
        if (userCurrent == null) {
            return new ResponseEntity<String>("login fail", HttpStatus.NO_CONTENT);
        }
        String result = jwtService.generateTokenLogin(userCurrent.getUserName(), userCurrent.getRole());
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/user/update")
    public ResponseEntity<User> updateUser(@RequestBody User user, HttpServletRequest request) {
        User userCurrent = getUserFromToken(request);
        if (userCurrent == null) {
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
        changeInfo(user, userCurrent);
        userService.save(userCurrent);
        return new ResponseEntity<User>(userCurrent, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/user/password")
    public ResponseEntity<User> changePassword(@RequestBody User user, HttpServletRequest request) {
        User userCurrent = getUserFromToken(request);
        if (userCurrent == null) {
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
        changePass(user, userCurrent);
        userService.save(userCurrent);
        return new ResponseEntity<User>(userCurrent, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public ResponseEntity<List<String>> Logout(HttpServletRequest request) {
        String token = request.getHeader("authorization");
        UserServiceImp.blackList.add(token);
        return new ResponseEntity<List<String>>(UserServiceImp.blackList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public ResponseEntity<List<User>> ListUser() {
        List<User> users = userService.getList();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/login-facebook", method = RequestMethod.GET)
    public ResponseEntity<String> loginFacebook(HttpServletRequest request) throws IOException {
        com.restfb.types.User user = getUserFb(request);
        String token = getToken(user);
        User existedUser = getExistedUser(user);
        if (existedUser != null) {
            return new ResponseEntity<String>(token, HttpStatus.OK);
        }
        User userAdded = makeUserFb(user);
        userService.save(userAdded);
        System.out.println("create user");
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

    public User getUserFromToken(HttpServletRequest request) {
        String userName = jwtService.getUsernameFromToken(request.getHeader("authorization"));
        return userService.findUserName(userName);
    }

    private void changePass(User user, User userCurrent) {
        userCurrent.setPassWord(user.getPassWord());
    }

    private User makeUserFb(com.restfb.types.User user) {
        User userAdded = new User();
        userAdded.setUserName(user.getId());
        userAdded.setPassWord("123123");
        return userAdded;
    }

    private String getToken(com.restfb.types.User user) {
        return jwtService.generateTokenLogin(user.getName(), "ROLE_USER");
    }

    private com.restfb.types.User getUserFb(HttpServletRequest request) throws IOException {
        String accessToken = getAccessToken(request);
        return restFb.getUserInfo(accessToken);
    }

    private String getAccessToken(HttpServletRequest request) throws IOException {
        String code = request.getParameter("code");
        return restFb.getToken(code);
    }

    private User getUser(User user) {
        List<User> users = userService.getList();
        return users.stream().filter(i -> user.getUserName().equals(i.getUserName()) &&
                user.getPassWord().equals(i.getPassWord())).findAny().orElse(null);
    }

    private User getExistedUser(com.restfb.types.User user) {
        List<User> users = userService.getList();
        return users.stream().filter(i -> i.getUserName().equals(user.getName())).findAny().orElse(null);
    }

    private void changeInfo(User user, User userCurrent) {
        if (user.getAddress() != null)
            userCurrent.setAddress(user.getAddress());
        if (user.getEmail() != null)
            userCurrent.setEmail(user.getEmail());
        if (user.getPhoneNumber() != null)
            userCurrent.setPhoneNumber(user.getPhoneNumber());
        if (user.getFirstName() != null)
            userCurrent.setFirstName(user.getFirstName());
        if (user.getLastName() != null)
            userCurrent.setLastName(user.getLastName());
        if (user.getSex() != null)
            userCurrent.setSex(user.getSex());
    }
}
