package com.projetoinvestimento.agregadorinvestimento.services;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.projetoinvestimento.agregadorinvestimento.controller.dtos.AccountStockDto;
import com.projetoinvestimento.agregadorinvestimento.entity.AccountStock;
import com.projetoinvestimento.agregadorinvestimento.entity.AccountStockId;
import com.projetoinvestimento.agregadorinvestimento.repository.AccountRepository;
import com.projetoinvestimento.agregadorinvestimento.repository.AccountStockRepository;
import com.projetoinvestimento.agregadorinvestimento.repository.StockRepository;


@Service
public class AccountService {
    private AccountStockRepository accountStockRepository;
    private StockRepository stockRepository;
    private AccountRepository accountRepository;

    
    public AccountService(AccountStockRepository accountStockRepository, StockRepository stockRepository,
            AccountRepository accountRepository) {
        this.accountStockRepository = accountStockRepository;
        this.stockRepository = stockRepository;
        this.accountRepository = accountRepository;
    }

    public void associtadedStockToAccount(String accountId, AccountStockDto accountStockDto){
        var account = accountRepository.findById(UUID.fromString(accountId))
                        .orElseThrow(()-> (new ResponseStatusException(HttpStatus.NOT_FOUND)));
        var stock = stockRepository.findById(accountStockDto.stockId())
                        .orElseThrow(() -> (new ResponseStatusException(HttpStatus.NOT_FOUND)));

        var accountStockId = new AccountStockId(
                                                account.getIdAccount(),
                                                stock.getStock_id()
        );

        var accountStock = new AccountStock(
                                            accountStockId,
                                            account,
                                            stock,
                                            accountStockDto.stockQuantity()
        );

        accountStockRepository.save(accountStock);
    }

}
