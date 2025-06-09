package com.richardmogou.Currency_exchange.controller;

import com.richardmogou.Currency_exchange.model.ConversionRequest;
import com.richardmogou.Currency_exchange.model.ConversionResponse;
import com.richardmogou.Currency_exchange.service.CurrencyConverterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/currency")
@RequiredArgsConstructor
@Tag(name="CurrencyConverterApiController")
public class CurrentCyConverterApiController {
    private final CurrencyConverterService converterService;

    @PostMapping("/convert")
    @Operation(summary = "Convertir un montant d'une devise à une autre",
            description = "Convertit un montant donné de la devise source vers la devise cible en utilisant les taux de change actuels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conversion réussie",
                    content = @Content(schema = @Schema(implementation = ConversionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "503", description = "Service de taux de change indisponible")
    })
    public ResponseEntity<ConversionResponse> convertCurrency(@RequestBody ConversionRequest request) {
        ConversionResponse response = converterService.convertCurrency(request);
        return ResponseEntity.ok(response);
    }
}
