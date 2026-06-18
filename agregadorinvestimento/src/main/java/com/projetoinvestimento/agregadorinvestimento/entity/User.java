package com.projetoinvestimento.agregadorinvestimento.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @Column(name = "username")
    private String username;
    
    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @CreationTimestamp
    private Instant creationTimeStamp;

    @UpdateTimestamp
    private Instant UpdateTimeStamp;

    /*
    One - a primeira palavra representa o papel no relacionamento da entidade em que está, neste caso um usuario
    To - Conexão do relacionamento
    Many - a terceira representa o papel da outra entidade, nesse caso varias contas
    */
    @OneToMany( mappedBy= "user")
    private List<Account> accounts;

    public User(UUID id, String username, String email,  String password, Instant creationTimeStamp,
            Instant updateTimeStamp) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.creationTimeStamp = creationTimeStamp;
        UpdateTimeStamp = updateTimeStamp;
    }

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(Instant creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public Instant getUpdateTimeStamp() {
        return UpdateTimeStamp;
    }

    public void setUpdateTimeStamp(Instant updateTimeStamp) {
        UpdateTimeStamp = updateTimeStamp;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    
    
}   
