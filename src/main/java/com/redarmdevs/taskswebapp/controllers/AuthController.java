package com.redarmdevs.taskswebapp.controllers;

import com.redarmdevs.taskswebapp.message.ResponseMessage;
import com.redarmdevs.taskswebapp.models.User;
import com.redarmdevs.taskswebapp.repositories.UserRepository;
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
    public ResponseEntity<ResponseMessage> signup(@RequestBody User user){
        String message;
        try {
            userService.signup(user);
            message = "Signed up Successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }
        catch (Exception e){
            message = "Failed to signup: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }
}
