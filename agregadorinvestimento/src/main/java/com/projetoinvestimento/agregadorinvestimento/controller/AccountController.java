package com.projetoinvestimento.agregadorinvestimento.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetoinvestimento.agregadorinvestimento.controller.dtos.AccountStockDto;
import com.projetoinvestimento.agregadorinvestimento.services.AccountService;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

     @PostMapping("/{accountId}/stock")
    public ResponseEntity<Void> associateStockToAccount(@PathVariable("accountId") String accountId,
                                                        @RequestBody AccountStockDto accountStockDto) {
        accountService.associtadedStockToAccount(accountId, accountStockDto);
        return ResponseEntity.ok().build();
        //return ResponseEntity.created(URI.create("/v1/stock/" + stockId.toString())).build();
    }
    
}
