package com.example.sosinventory.sale;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sales")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)

public class SaleController {

    private final SaleService salesService;

    @PostMapping
    public ResponseEntity<Sale> createSale(@RequestBody SaleRequest sale) {
        salesService.createSale(sale);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Sale>> getAllSales() {
        List<Sale> sales = salesService.getAllSales();
        return ResponseEntity.ok(sales);
    }
}
