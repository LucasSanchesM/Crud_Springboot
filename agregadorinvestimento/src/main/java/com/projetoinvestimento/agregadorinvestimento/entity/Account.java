package com.projetoinvestimento.agregadorinvestimento.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "id_account")
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID idAccount;

    @Column(name = "account_description")
    private String description;

    public Account() {
    }

    public Account(UUID idAccount, String description) {
        this.idAccount = idAccount;
        this.description = description;
    }

    public UUID getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(UUID idAccount) {
        this.idAccount = idAccount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    

}
