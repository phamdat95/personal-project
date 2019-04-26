package com.example.demo.controller;

import com.example.demo.configToken.JwtService;
import com.example.demo.model.Image;
import com.example.demo.model.LikeImage;
import com.example.demo.model.User;
import com.example.demo.service.ImageService;
import com.example.demo.service.LikeImageService;
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
public class LikeImageController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private LikeImageService likeImageService;
    @Autowired
    private ImageService imageService;

    @RequestMapping(method = RequestMethod.POST, value = "/like/{id}")
    public ResponseEntity<LikeImage> likeImage (@PathVariable("id") Long id, HttpServletRequest request) {
        LikeImage likeImage = getLikeImage(id, request);
        likeImageService.save(likeImage);
        return new ResponseEntity<LikeImage>(likeImage, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/unlike/{id}")
    public ResponseEntity<String> unlikeImage (@PathVariable("id") Long id) {
        likeImageService.delete(id);
        return new ResponseEntity<String>("unlike success", HttpStatus.OK);
    }

    private LikeImage getLikeImage(Long id, HttpServletRequest request) {
        User user = getUserLike(request);
        Image image = imageService.findById(id);
        return new LikeImage(image.getImageName(), user);
    }

    private User getUserLike(HttpServletRequest request) {
        String name = jwtService.getUsernameFromToken(request.getHeader("authorization"));
        return userService.findUserName(name);
    }
}
