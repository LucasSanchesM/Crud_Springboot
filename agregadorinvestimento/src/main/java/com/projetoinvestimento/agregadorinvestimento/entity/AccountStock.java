package com.projetoinvestimento.agregadorinvestimento.entity;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "account_stock")
public class AccountStock {

    @EmbeddedId
    private AccountStockId id;


    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_Id")
    private Account account;


    @ManyToOne
    @MapsId("stockId")
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    public AccountStock() {
    }

    public AccountStock(AccountStockId id, Account account, Stock stock, Integer stockQuantity) {
        this.id = id;
        this.account = account;
        this.stock = stock;
        this.stockQuantity = stockQuantity;
    }

    public AccountStockId getId() {
        return id;
    }

    public void setId(AccountStockId id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    
}
