package com.richardmogou.Currency_exchange.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversionResponse {
    @Schema(description = "Code de la devise source", example = "EUR")
    private String fromCurrency;

    @Schema(description = "Code de la devise cible", example = "USD")
    private String toCurrency;

    @Schema(description = "Montant initial", example = "100.00")
    private BigDecimal originalAmount;

    @Schema(description = "Montant converti", example = "108.50")
    private BigDecimal convertedAmount;

    @Schema(description = "Taux de change utilisé", example = "1.085")
    private BigDecimal exchangeRate;
}
