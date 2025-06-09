package com.richardmogou.Currency_exchange.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionRequest {
    @Schema(description = "Code de la devise source (ex: EUR)", example = "EUR", required = true)
    private String fromCurrency;

    @Schema(description = "Code de la devise cible (ex: USD)", example = "USD", required = true)
    private String toCurrency;

    @Schema(description = "Montant à convertir", example = "100.00", required = true)
    private BigDecimal amount;
}
