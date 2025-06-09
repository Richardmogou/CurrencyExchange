package com.richardmogou.Currency_exchange.service;

import com.richardmogou.Currency_exchange.exception.CurrencyConversionException;
import com.richardmogou.Currency_exchange.model.ConversionRequest;
import com.richardmogou.Currency_exchange.model.ConversionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyConverterService {
    private final ExchangeRateService exchangeRateService;
    public ConversionResponse convertCurrency(ConversionRequest request) {
        validateRequest(request);

        String fromCurrency = request.getFromCurrency().toUpperCase();
        String toCurrency = request.getToCurrency().toUpperCase();
        BigDecimal amount = request.getAmount();

        try {
            BigDecimal exchangeRate = exchangeRateService.getExchangeRate(fromCurrency, toCurrency);
            BigDecimal convertedAmount = amount.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);

            return ConversionResponse.builder()
                    .fromCurrency(fromCurrency)
                    .toCurrency(toCurrency)
                    .originalAmount(amount)
                    .convertedAmount(convertedAmount)
                    .exchangeRate(exchangeRate)
                    .build();

        } catch (Exception e) {
            log.error("Erreur lors de la conversion de devise", e);
            throw new CurrencyConversionException("Erreur lors de la conversion: " + e.getMessage());
        }
    }

    private void validateRequest(ConversionRequest request) {
        if (request.getFromCurrency() == null || request.getFromCurrency().trim().isEmpty()) {
            throw new CurrencyConversionException("La devise source est requise");
        }

        if (request.getToCurrency() == null || request.getToCurrency().trim().isEmpty()) {
            throw new CurrencyConversionException("La devise cible est requise");
        }

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CurrencyConversionException("Le montant doit être supérieur à zéro");
        }
    }

}
