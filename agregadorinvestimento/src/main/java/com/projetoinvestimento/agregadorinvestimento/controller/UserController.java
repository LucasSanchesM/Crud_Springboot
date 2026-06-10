package com.projetoinvestimento.agregadorinvestimento.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetoinvestimento.agregadorinvestimento.entity.User;
import com.projetoinvestimento.agregadorinvestimento.services.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;
    

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto body){
        var userId = userService.createUser(body);
        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
    }

    @GetMapping("/{userid}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userid){
        return null;
    }
    
}
