package com.redarmdevs.taskswebapp.controllers;

import com.redarmdevs.taskswebapp.exceptions.UserException;
import com.redarmdevs.taskswebapp.models.User;
import com.redarmdevs.taskswebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @PostMapping(path = "signup")
    public ResponseEntity<String> signup(@RequestBody User user){
        String message;
        try {
            userService.signup(user);
            message = user.getUsername() + " Signed up Successfully";
            return ResponseEntity.ok(message);
        }
        catch (UserException e){
            message = "Failed to signup: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }
}
