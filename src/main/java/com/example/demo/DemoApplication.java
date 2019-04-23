package com.example.demo;

import com.example.demo.configToken.JwtService;
import com.example.demo.service.*;
import com.example.demo.service.imp.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public UserService userService() {
        return new UserServiceImp();
    }

    @Bean
    public JwtService jwtService() {
        return new JwtService();
    }

    @Bean
    public FriendService friendService() {
        return new FriendServiceImp();
    }

    @Bean
    public ImageService imageService() {
        return new ImageServiceImp();
    }

    @Bean
    public LikeImageService likeImageService() {
        return new LikeImageServiceImp();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceImp();
    }
}

