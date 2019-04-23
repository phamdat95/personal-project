package com.example.demo.controller;

import com.example.demo.configToken.JwtService;
import com.example.demo.model.Comment;
import com.example.demo.model.Image;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import com.example.demo.service.ImageService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CommentController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CommentService commentService;

    @RequestMapping(method = RequestMethod.POST, value = "/comment/{id}")
    public ResponseEntity<Comment> addComment (@PathVariable("id") Long id, HttpServletRequest request, @RequestBody Comment comment){
        String name = jwtService.getUsernameFromToken(request.getHeader("authorization"));
        User user = userService.findUserName(name);
        Image image = imageService.findById(id);
        comment.setImage(image);
        comment.setUser(user);
        commentService.save(comment);
        return new ResponseEntity<Comment>(comment, HttpStatus.OK);
    }
}
