package com.dsi.authredis.service;


import com.dsi.authredis.entity.User;
import com.dsi.authredis.repository.UserRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.Optional;

@Stateless
public class UserService {
    @EJB
    private UserRepository userRepository;

    public Optional<User> getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        return user;
    }

    public Optional<User> getUserByPhoneNumberAndPassword(String phoneNumber, String password) {
        Optional<User> user = userRepository.findByPhoneNumberAndPassword(phoneNumber, password);
        return user;
    }

    public Optional<User> getUserByEmailAndPassword(String email, String password) {
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        return userRepository.findByEmailAndPassword(email, password);
    }
}