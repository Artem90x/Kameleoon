package com.example.kameleoon.controller;

import com.example.kameleoon.exception.UserAlreadyExistException;
import com.example.kameleoon.model.entity.User;
import com.example.kameleoon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body("User with email " + user.getEmail() + " already exists.");
        }
    }
}
