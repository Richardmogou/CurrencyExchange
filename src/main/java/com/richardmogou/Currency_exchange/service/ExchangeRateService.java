package com.richardmogou.Currency_exchange.service;

import com.richardmogou.Currency_exchange.exception.ExchangeRateException;
import com.richardmogou.Currency_exchange.model.ExchangeRateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateService {
    private final WebClient webClient;
    private final Map<String, ExchangeRateResponse> rateCache= new ConcurrentHashMap<>();
    private final Map<String, Long> cacheTimestamps= new ConcurrentHashMap<>();

    @Value("${exchange.api.key:open_source_key}")
    private String apiKey;

    private static final String API_URL = "https://open.er-api.com/v6/latest/";
    private static final long CACHE_DURATION_MS = 3600000; // 1 heure

    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        try {
            ExchangeRateResponse response = getExchangeRateData(fromCurrency);

            if (!response.getRates().containsKey(toCurrency)) {
                throw new ExchangeRateException("Devise cible non supportée: " + toCurrency);
            }

            Double rate = response.getRates().get(toCurrency);
            return BigDecimal.valueOf(rate).setScale(6, RoundingMode.HALF_UP);

        } catch (WebClientResponseException e) {
            log.error("Erreur lors de l'appel à l'API de taux de change", e);
            throw new ExchangeRateException("Erreur lors de la récupération des taux de change: " + e.getMessage());
        }
    }

    private ExchangeRateResponse getExchangeRateData(String baseCurrency) {
        // Vérifier si les données sont en cache et encore valides
        if (rateCache.containsKey(baseCurrency)) {
            long timestamp = cacheTimestamps.getOrDefault(baseCurrency, 0L);
            if (System.currentTimeMillis() - timestamp < CACHE_DURATION_MS) {
                return rateCache.get(baseCurrency);
            }
        }

        // Récupérer les données fraîches
        ExchangeRateResponse response = webClient.get()
                .uri(API_URL + baseCurrency)
                .retrieve()
                .bodyToMono(ExchangeRateResponse.class)
                .timeout(Duration.ofSeconds(10))
                .block();

        // Mettre en cache
        if (response != null) {
            rateCache.put(baseCurrency, response);
            cacheTimestamps.put(baseCurrency, System.currentTimeMillis());
        }

        return response;
    }
}
