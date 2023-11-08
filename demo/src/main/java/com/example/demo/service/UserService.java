package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    User createUser(User user);

    Optional<User> findById(Long id);
}
