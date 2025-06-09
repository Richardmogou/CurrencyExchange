package com.richardmogou.Currency_exchange.exception;

import com.richardmogou.Currency_exchange.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Controller
public class GlobalExceptionHandler {
    @ExceptionHandler(CurrencyConversionException.class)
    public ResponseEntity<ErrorResponse> handleCurrencyConversionException(CurrencyConversionException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .message(ex.getMessage())
                .code("CONVERSION_ERROR")
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExchangeRateException.class)
    public ResponseEntity<ErrorResponse> handleExchangeRateException(ExchangeRateException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .message(ex.getMessage())
                .code("EXCHANGE_RATE_ERROR")
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = ErrorResponse.builder()
                .message("Une erreur inattendue s'est produite: " + ex.getMessage())
                .code("INTERNAL_ERROR")
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
