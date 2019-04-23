package com.example.demo.service.imp;

import com.example.demo.model.LikeImage;
import com.example.demo.repository.LikeImageRepository;
import com.example.demo.service.LikeImageService;
import org.springframework.beans.factory.annotation.Autowired;

public class LikeImageServiceImp implements LikeImageService {
    @Autowired
    private LikeImageRepository likeImageRepository;

    @Override
    public void save(LikeImage likeImage) {
        likeImageRepository.save(likeImage);
    }

    @Override
    public void delete(Long id) {
        likeImageRepository.deleteById(id);
    }
}
