package com.richardmogou.Currency_exchange.controller;

import com.richardmogou.Currency_exchange.model.ConversionRequest;
import com.richardmogou.Currency_exchange.model.ConversionResponse;
import com.richardmogou.Currency_exchange.service.CurrencyConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CurrencyCOnverterWebController {
    private final CurrencyConverterService converterService;

    private static final List<String> COMMON_CURRENCIES = Arrays.asList(
            "USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CHF", "CNY", "INR", "BRL"
    );

    @GetMapping("/")
    public String showConverterForm(Model model) {
        model.addAttribute("conversionRequest", new ConversionRequest());
        model.addAttribute("currencies", COMMON_CURRENCIES);
        return "converter";
    }

    @PostMapping("/convert")
    public String convertCurrency(@ModelAttribute ConversionRequest request, Model model) {
        try {
            ConversionResponse response = converterService.convertCurrency(request);
            model.addAttribute("result", response);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("conversionRequest", request);
        model.addAttribute("currencies", COMMON_CURRENCIES);
        return "converter";
    }
}
