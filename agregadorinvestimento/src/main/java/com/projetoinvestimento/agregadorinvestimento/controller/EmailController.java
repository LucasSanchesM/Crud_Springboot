package com.projetoinvestimento.agregadorinvestimento.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.projetoinvestimento.agregadorinvestimento.services.AccountService;
import com.projetoinvestimento.agregadorinvestimento.services.EmailService;
import com.projetoinvestimento.agregadorinvestimento.services.UserService;


@RestController
@RequestMapping("/v1/sendemails")
public class EmailController {
    private EmailService emailService;

    



    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }





    @PostMapping("/{userId}/stocks")
    public ResponseEntity<Void> eviarAcoesViaEmail(@PathVariable("userId") String userId) {
        
        emailService.enviarEmailComAcoes(userId);
        return ResponseEntity.ok().build();
    }
    
}
