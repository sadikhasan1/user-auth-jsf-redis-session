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

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public Optional<User> getUserByPhoneNumberAndPassword(String phoneNumber, String password) {
        return userRepository.findByPhoneNumberAndPassword(phoneNumber, password);
    }

    public Optional<User> getUserByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
