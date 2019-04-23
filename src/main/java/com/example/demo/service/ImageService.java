package com.example.demo.service;

import com.example.demo.model.Image;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    List<Image> findAll();

    Resource findOneImage(String name);

    void create(MultipartFile file, Image image) throws IOException;

    void delete(String name) throws IOException;

    Image findById (Long id);
}
