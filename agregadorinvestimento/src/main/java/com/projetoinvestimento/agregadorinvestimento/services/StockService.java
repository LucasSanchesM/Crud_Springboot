package com.projetoinvestimento.agregadorinvestimento.services;

import org.springframework.stereotype.Service;

import com.projetoinvestimento.agregadorinvestimento.controller.dtos.CreateStockDto;
import com.projetoinvestimento.agregadorinvestimento.entity.Stock;
import com.projetoinvestimento.agregadorinvestimento.repository.StockRepository;

@Service
public class StockService {
    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto createStockDto){
        var stockCreated = new Stock(createStockDto.stockId(),  createStockDto.description());
        var stockSaved = stockRepository.save(stockCreated);
    }
}
