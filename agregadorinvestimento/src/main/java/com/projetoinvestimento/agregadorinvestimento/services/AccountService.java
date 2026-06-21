package com.projetoinvestimento.agregadorinvestimento.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.projetoinvestimento.agregadorinvestimento.client.BrapiClient;
import com.projetoinvestimento.agregadorinvestimento.controller.dtos.AccountStockDto;
import com.projetoinvestimento.agregadorinvestimento.controller.dtos.AccountStockReponseDto;
import com.projetoinvestimento.agregadorinvestimento.entity.AccountStock;
import com.projetoinvestimento.agregadorinvestimento.entity.AccountStockId;
import com.projetoinvestimento.agregadorinvestimento.repository.AccountRepository;
import com.projetoinvestimento.agregadorinvestimento.repository.AccountStockRepository;
import com.projetoinvestimento.agregadorinvestimento.repository.StockRepository;


@Service
public class AccountService {
    
    @Value("#{environment.TOKEN}")
    private String TOKEN;

    private AccountStockRepository accountStockRepository;
    private StockRepository stockRepository;
    private AccountRepository accountRepository;
    private BrapiClient brapiClient;
    
    

    public AccountService(AccountStockRepository accountStockRepository, StockRepository stockRepository,
            AccountRepository accountRepository, BrapiClient brapiClient) {
        this.accountStockRepository = accountStockRepository;
        this.stockRepository = stockRepository;
        this.accountRepository = accountRepository;
        this.brapiClient = brapiClient;
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

    public List<AccountStockReponseDto> getAccountStocks(String accountId){
        var account = accountRepository.findById(UUID.fromString(accountId))
                        .orElseThrow(()->(new ResponseStatusException(HttpStatus.NOT_FOUND)));

        return account.getAccountStocks()
                                    .stream()
                                    .map(as -> new AccountStockReponseDto(as.getStock().getStock_id(), as.getStockQuantity(), getTotal(as.getStock().getStock_id(), as.getStockQuantity()))).toList();

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
