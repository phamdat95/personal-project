package com.example.demo.repository;

import com.example.demo.model.Friend;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FriendRepository extends PagingAndSortingRepository<Friend, Long> {
}
