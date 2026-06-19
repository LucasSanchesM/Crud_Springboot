package com.projetoinvestimento.agregadorinvestimento.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetoinvestimento.agregadorinvestimento.controller.dtos.CreateStockDto;
import com.projetoinvestimento.agregadorinvestimento.services.StockService;


@RestController
@RequestMapping("/v1/stock")
public class StockController {
    private StockService stockService;

    
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }


    @PostMapping
    public ResponseEntity<Void> CriarStock(@RequestBody CreateStockDto createStockDto) {
        /*var stockId= */stockService.createStock(createStockDto);
        return ResponseEntity.ok().build();
        //return ResponseEntity.created(URI.create("/v1/stock/" + stockId.toString())).build();
    }
    

}
