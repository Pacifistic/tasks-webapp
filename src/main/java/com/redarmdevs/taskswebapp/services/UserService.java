package com.redarmdevs.taskswebapp.services;

import com.redarmdevs.taskswebapp.exceptions.UserException;
import com.redarmdevs.taskswebapp.models.User;
import com.redarmdevs.taskswebapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;

    public void signup(User user) throws UserException {
        if(userRepo.existsByUsername(user.getUsername()))
            throw new UserException("Username taken");
        if(userRepo.existsByEmail(user.getEmail()))
            throw new UserException("Email already in use");
        userRepo.save(user);
    }
}
