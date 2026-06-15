package com.TTCS26.financeTracker.controller;

import com.TTCS26.financeTracker.dto.ExchangeRateApiResponse;
import com.TTCS26.financeTracker.model.CurrencyRate;
import com.TTCS26.financeTracker.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    // Lấy tỉ giá
    @GetMapping("/{currency}")
    public ResponseEntity<?> getExchangeRates(@PathVariable String currency) {
        log.info("Lấy tỷ giá của {}", currency);

        try {
            ExchangeRateApiResponse response = exchangeRateService.getExchangeRates(currency);

            Set<String> compareList = new HashSet<>(Arrays.asList("USD", "VND"));

            Map<String, Double> filteredRates = new java.util.HashMap<>();
            for (String target : compareList) {
                Double rate = response.getConversionRates().get(target);
                if (rate != null) {
                    filteredRates.put(target, rate);

                    if (!currency.equalsIgnoreCase(target)) {
                        exchangeRateService.saveRateToDatabase(currency, target);
                    }
                }
            }

            return ResponseEntity.ok(Map.of(
                    "base", currency.toUpperCase(),
                    "saved_to_db", compareList,
                    "rates", filteredRates
            ));

        } catch (Exception e) {
            log.error("Lỗi không tìm được tỉ giá: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Xem lịch sử tỷ giá
    @GetMapping("/history")
    public ResponseEntity<?> getRateHistory(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(defaultValue = "7") int days
    ) {
        log.info("Lấy lịch sử {} -> {} trong {} ngày", from, to, days);

        try {
            List<CurrencyRate> history = exchangeRateService.getHistory(from, to, days);

            for (CurrencyRate item : history) {
                log.info("Ngày {}: {} to {} = {}",
                        item.getFetchDate().toLocalDate(),
                        item.getBaseCurrency(),
                        item.getTargetCurrency(),
                        item.getRate());
            }

            return ResponseEntity.ok(Map.of(
                    "from", from.toUpperCase(),
                    "to", to.toUpperCase(),
                    "days", days,
                    "totalRecords", history.size(),
                    "data", history
            ));
        } catch (Exception e) {
            log.error("Lỗi khi lấy lịch sử: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Quy đổi
    @GetMapping("/convert")
    public ResponseEntity<?> convertCurrency(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(defaultValue = "1") double amount
    ) {
        log.info("Quy đổi {} {} sang {}", amount, from, to);

        try {
            double result = exchangeRateService.convertCurrency(from, to, amount);

            ExchangeRateApiResponse apiResponse = exchangeRateService.getExchangeRates(from);
            double rate = apiResponse.getConversionRates().get(to.toUpperCase());

            return ResponseEntity.ok(Map.of(
                    "from", from.toUpperCase(),
                    "to", to.toUpperCase(),
                    "amount", amount,
                    "result", String.format("%.2f", result),
                    "rate", String.format("%.4f", rate)
            ));

        } catch (Exception e) {
            log.error("Lỗi quy đổi: {}", e.getMessage());
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        }
    }

    // Kiểm tra tỉ giá mới nhất
    @GetMapping("/db/latest")
    public ResponseEntity<?> getLatestFromDb(
            @RequestParam String from,
            @RequestParam String to
    ) {
        log.info("Lấy từ DB {} → {}", from, to);

        Double rate = exchangeRateService.getLatestRateFromDb(from, to);

        if (rate == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(Map.of(
                "from", from.toUpperCase(),
                "to", to.toUpperCase(),
                "latestRate", rate
        ));
    }
}