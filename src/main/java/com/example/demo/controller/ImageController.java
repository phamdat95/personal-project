package com.example.demo.controller;

import com.example.demo.configToken.JwtService;
import com.example.demo.model.Image;
import com.example.demo.model.User;
import com.example.demo.service.ImageService;
import com.example.demo.service.UserService;
import com.example.demo.service.imp.ImageServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class ImageController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @RequestMapping(value = "/image/",method = RequestMethod.POST)
    public ResponseEntity<String> createImage(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request) throws IOException {
        User userCurrent = getUserFromToken(request);
        Image image = new Image();
        image.setUser(userCurrent);
        imageService.create(file, image);
        return new ResponseEntity<String>("upload success", HttpStatus.OK);
    }

    @RequestMapping(value = "/image/update/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<String> updateImage (@PathVariable("id") Long id, @RequestParam(name = "file") MultipartFile file) throws IOException {
        Image image = imageService.findById(id);
        Files.deleteIfExists(Paths.get(ImageServiceImp.UPLOAD, image.getImageName()));
        imageService.create(file, image);
        return new ResponseEntity<String>("update success", HttpStatus.OK);
    }

    @GetMapping("/images/{id}/raw")
    public ResponseEntity<?> oneRawImage (@PathVariable("id") Long id){
        try {
            Image image = imageService.findById(id);
            Resource file = imageService.findOneImage(image.getImageName());
            return ResponseEntity.ok().
                    contentLength(file.contentLength()).
                    contentType(MediaType.IMAGE_JPEG).
                    body(new InputStreamResource(file.getInputStream()));
        } catch (IOException e){
            return ResponseEntity.badRequest().body("couldn't find");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/image/get/{id}")
    public ResponseEntity<String> getLink (@PathVariable("id") Long id) {
        Image image = imageService.findById(id);
        String url = "http://localhost:8080/images/" + image.getId() + "/raw";
        return new ResponseEntity<String>(url, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/image/delete/{id}")
    public ResponseEntity<String> deleteFile (@PathVariable("id") Long id) throws IOException {
        Image image = imageService.findById(id);
        imageService.delete(image.getImageName());
        return new ResponseEntity<String>("delete success", HttpStatus.OK);
    }

    private User getUserFromToken(HttpServletRequest request) {
        String userName = jwtService.getUsernameFromToken(request.getHeader("authorization"));
        return userService.findUserName(userName);
    }
}
