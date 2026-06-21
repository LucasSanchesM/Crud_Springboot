package com.projetoinvestimento.agregadorinvestimento.controller.dtos;

import java.util.List;

import com.projetoinvestimento.agregadorinvestimento.entity.AccountStock;

public record ContentResponseDto(String nome, String conta,  List<AccountStock> acoes) {

}
