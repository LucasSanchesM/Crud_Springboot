package com.projetoinvestimento.agregadorinvestimento.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.projetoinvestimento.agregadorinvestimento.controller.dtos.AccountResponseDto;
import com.projetoinvestimento.agregadorinvestimento.controller.dtos.CreateAccountDto;
import com.projetoinvestimento.agregadorinvestimento.controller.dtos.CreateUserDto;
import com.projetoinvestimento.agregadorinvestimento.controller.dtos.UpdateUserDto;
import com.projetoinvestimento.agregadorinvestimento.entity.Account;
import com.projetoinvestimento.agregadorinvestimento.entity.BillingAdress;
import com.projetoinvestimento.agregadorinvestimento.entity.User;
import com.projetoinvestimento.agregadorinvestimento.repository.AccountRepository;
import com.projetoinvestimento.agregadorinvestimento.repository.BillingAdressRepository;
import com.projetoinvestimento.agregadorinvestimento.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    private AccountRepository accountRepository;
    private BillingAdressRepository billingAdressRepository;

    



    public UserService(UserRepository userRepository, AccountRepository accountRepository,
            BillingAdressRepository billingAdressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAdressRepository = billingAdressRepository;
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

    public void createAccount(String userId, CreateAccountDto createAccountDto){

        var user = userRepository.findById(UUID.fromString(userId)).
                            orElseThrow( ()->(new ResponseStatusException(HttpStatus.NOT_FOUND)));
        var account = new Account(
                                null,
                                createAccountDto.description(),
                                user,
                                null,
                                new ArrayList<>()

        );
        var accountcreated = accountRepository.save(account);
        var billingAdress = new BillingAdress(
                                accountcreated.getIdAccount(),
                                createAccountDto.street(),
                                createAccountDto.number(),
                                account

        );
        billingAdressRepository.save(billingAdress);
       
    }


    public List<AccountResponseDto> listAccounts (String userId){
        var user = userRepository.findById(UUID.fromString(userId)).
                            orElseThrow( ()->(new ResponseStatusException(HttpStatus.NOT_FOUND)));

        var listAccounts = user.getAccounts().stream().map(ac -> new AccountResponseDto(ac.getIdAccount().toString(), ac.getDescription())).toList();
        return listAccounts;
    }   
}
