package com.projetoinvestimento.agregadorinvestimento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetoinvestimento.agregadorinvestimento.entity.AccountStock;
import com.projetoinvestimento.agregadorinvestimento.entity.AccountStockId;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {

}
