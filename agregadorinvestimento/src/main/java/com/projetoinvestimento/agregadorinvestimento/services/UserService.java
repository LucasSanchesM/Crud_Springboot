package com.projetoinvestimento.agregadorinvestimento.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.projetoinvestimento.agregadorinvestimento.controller.CreateUserDto;
import com.projetoinvestimento.agregadorinvestimento.controller.UpdateUserDto;
import com.projetoinvestimento.agregadorinvestimento.entity.User;
import com.projetoinvestimento.agregadorinvestimento.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public UUID createUser(CreateUserDto createUserDto) {

        var user =new User(null,
        createUserDto.username(),
        createUserDto.email(), 
        createUserDto.password(),
        Instant.now(),
        null);

        var userSaved = userRepository.save(user);
        return userSaved.getId();
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }

    public void deleteUserById(String userId){
        var id = UUID.fromString(userId);
        var userExist = userRepository.existsById(id);
        if(userExist){
            userRepository.deleteById(id);
        }
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto){
        var id = UUID.fromString(userId);
        var userEntity = userRepository.findById(id);
        if(userEntity.isPresent()){
            if(updateUserDto.username() != null){
                userEntity.get().setUsername(updateUserDto.username());
            }
            if(updateUserDto.password() != null){
                userEntity.get().setPassword(updateUserDto.password());
            }
            userRepository.save(userEntity.get());
        }
    }


}
