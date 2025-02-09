package com.example.ecommerce_platform.user_service.service;

import com.example.ecommerce_platform.user_service.model.User;
import com.example.ecommerce_platform.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
       return  userRepository.findAll();
    }
    public Optional<User> getUser(String id){
        return userRepository.findById(id);
    }
    public User createUser(User user){
        return userRepository.save(user);
    }
}
