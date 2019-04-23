package com.example.demo.service;

import com.example.demo.model.LikeImage;

public interface LikeImageService {
    void save (LikeImage likeImage);

    void delete (Long id);

}
