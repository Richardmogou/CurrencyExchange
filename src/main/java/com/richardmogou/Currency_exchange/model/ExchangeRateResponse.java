package com.richardmogou.Currency_exchange.model;

import lombok.Data;

import java.util.Map;
@Data
public class ExchangeRateResponse {
    private String result;
    private String base;
    private Map<String, Double> rates;
}
