package com.projetoinvestimento.agregadorinvestimento.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
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

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @OneToOne(mappedBy = "account")
    @PrimaryKeyJoinColumn
    private BillingAdress andress;  

    @OneToMany(mappedBy = "account")
    private List<AccountStock> accountStocks;


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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
    
}
