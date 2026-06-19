package com.projetoinvestimento.agregadorinvestimento.entity;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "billing_adress")
public class BillingAdress {
    
    @Id
    @Column(name = "id_account")
    private UUID id_account;
    
    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name  = "id_account")
    private Account account;

    public BillingAdress() {
    }

    

    public BillingAdress(UUID id_account, String street, Integer number, Account account) {
        this.id_account = id_account;
        this.street = street;
        this.number = number;
        this.account = account;
    }



    public UUID getId_account() {
        return id_account;
    }

    public void setId_account(UUID id_account) {
        this.id_account = id_account;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    } 

    

}
