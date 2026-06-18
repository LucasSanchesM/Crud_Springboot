package com.projetoinvestimento.agregadorinvestimento.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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


}
