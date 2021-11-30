package com.creighbattle.services;

import com.creighbattle.domain.User;
import com.creighbattle.exceptions.UsernameAlreadyExistsException;
import com.creighbattle.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser) {

        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());
            //Make sure that password and confirmPassword match
            newUser.setConfirmPassword("");
            //We don't persist or show the confirmPassword
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists.");
        }
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

}
