package com.example.ecommerce_platform.user_service.service;

import com.example.ecommerce_platform.user_service.model.User;
import com.example.ecommerce_platform.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers(){
       return  userRepository.findAll();
    }
    public Optional<User> getUser(String id){
        return userRepository.findById(id);
    }
    public User createUser(User user) {
        // Encode the plaintext password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Save the user with the hashed password
        return userRepository.save(user);
    }
}
