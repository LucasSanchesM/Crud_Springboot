package com.projetoinvestimento.agregadorinvestimento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetoinvestimento.agregadorinvestimento.entity.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

}
