package com.richardmogou.Currency_exchange.exception;

public class ExchangeRateException extends RuntimeException {
    public ExchangeRateException(String message) {
        super(message);
    }
}
