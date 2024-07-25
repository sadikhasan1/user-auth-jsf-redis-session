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
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public Optional<User> getUserByPhoneNumberAndPassword(String phoneNumber, String password) {
        return userRepository.findByPhoneNumberAndPassword(phoneNumber, password);
    }

    public Optional<User> getUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
