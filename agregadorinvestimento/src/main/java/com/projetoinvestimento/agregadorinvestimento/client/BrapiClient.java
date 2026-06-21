package com.projetoinvestimento.agregadorinvestimento.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetoinvestimento.agregadorinvestimento.client.dtos.BrapiResponseDto;

@FeignClient(
        name = "BrapiClient",
        url = "https://brapi.dev"
)
public interface BrapiClient {
    @GetMapping(value = "/api/v2/stocks/quote?symbols={stockId}")
    BrapiResponseDto getQuote(@RequestParam("token") String token, @PathVariable("stockId") String stockID);
}
