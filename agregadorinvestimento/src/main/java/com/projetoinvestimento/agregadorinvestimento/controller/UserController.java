package com.projetoinvestimento.agregadorinvestimento.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId){
        var user = userService.getUserById(userId);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> listarUsuarios(){
        var user = userService.listarUsuarios();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId,
                                                @RequestBody UpdateUserDto body){
        userService.updateUserById(userId, body);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") String userId){
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }
    
}
