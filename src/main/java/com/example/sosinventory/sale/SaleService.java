package com.example.sosinventory.sale;

import java.util.List;

public interface SaleService {
    String createSale(SaleRequest saleRequest);
    List<Sale> getAllSales();
}
