package com.main.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.main.model.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email); // Add this method
}

