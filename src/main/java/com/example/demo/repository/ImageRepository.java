package com.example.demo.repository;

import com.example.demo.model.Image;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ImageRepository extends PagingAndSortingRepository<Image, Long> {
    Image findByImageName (String name);
}
