package com.projetoinvestimento.agregadorinvestimento.services;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.projetoinvestimento.agregadorinvestimento.client.BrapiClient;
import com.projetoinvestimento.agregadorinvestimento.controller.dtos.AccountResponseDto;
import com.projetoinvestimento.agregadorinvestimento.controller.dtos.ContentResponseDto;
import com.projetoinvestimento.agregadorinvestimento.controller.dtos.ResponseEmailDto;
import com.projetoinvestimento.agregadorinvestimento.entity.AccountStock;
import com.projetoinvestimento.agregadorinvestimento.repository.AccountStockRepository;
import com.projetoinvestimento.agregadorinvestimento.repository.UserRepository;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    private UserRepository userRepository;
    private BrapiClient brapiClient;    
    
    @Value("#{environment.TOKEN}")
    private String TOKEN;


    public EmailService(JavaMailSender mailSender, UserRepository userRepository, BrapiClient brapiClient) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
        this.brapiClient = brapiClient;
    }

    public void enviarEmailComAcoes(String userId){
        var user = userRepository.findById(UUID.fromString(userId))
                    .orElseThrow(()-> (new ResponseStatusException(HttpStatus.NOT_FOUND)));
        var listAccounts = user.getAccounts().stream()
                        .map(ac -> new ContentResponseDto(user.getUsername(), ac.getDescription(), ac.getAccountStocks())).toList();
        String corpoDaMensagem = listAccounts.get(0).nome();
        for(ContentResponseDto content : listAccounts){
            corpoDaMensagem += "\n" + content.conta().toString();
            for(AccountStock stock : content.acoes()){
                corpoDaMensagem += "\n" + stock.getStock().getDescription() + "\nQuantidade:" + stock.getStockQuantity() + "\n";
                corpoDaMensagem += getTotal(stock.getStock().getStock_id(), stock.getStockQuantity());
            }
            
        }

        SimpleMailMessage message =  new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Ola, estas são as suas ações atualmente obtidas!");
        message.setText(corpoDaMensagem);
        mailSender.send(message);
    }

    private double getTotal(String stockId, Integer stockQuantity){
        var response = brapiClient.getQuote(TOKEN, stockId);

        System.out.println("RESPONSE: " + response);

        if (response == null) {
            throw new RuntimeException("Sem resultados da API para stockId: " + stockId);
        }
        var price = response.results().getFirst().data().regularMarketPrice();

        System.out.println("PRICE: " + price + " QTD: " + stockQuantity);

        return price*stockQuantity;
    }
    
    
}
