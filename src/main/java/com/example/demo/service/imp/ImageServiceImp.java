package com.example.demo.service.imp;

import com.example.demo.model.Image;
import com.example.demo.repository.ImageRepository;
import com.example.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ImageServiceImp implements ImageService {
    public static String UPLOAD = "upload-dir";

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public List<Image> findAll() {
        return (List<Image>) imageRepository.findAll();
    }

    @Override
    public Resource findOneImage(String name) {
        return resourceLoader.getResource("file:"+UPLOAD+"/"+name);
    }

    @Override
    public void create(MultipartFile file, Image image) throws IOException {
        if (!file.isEmpty()){
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            Files.copy(file.getInputStream(), Paths.get(UPLOAD, fileName));
            image.setImageName(fileName);
            imageRepository.save(image);

        }
    }

    @Override
    public void delete(String name) throws IOException {
        Image byName = imageRepository.findByImageName(name);
        imageRepository.delete(byName);
        Files.deleteIfExists(Paths.get(UPLOAD, name));
    }

    @Override
    public Image findById(Long id) {
        return imageRepository.findById(id).get();
    }
}
