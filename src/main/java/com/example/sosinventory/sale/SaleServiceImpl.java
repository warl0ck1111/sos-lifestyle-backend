package com.example.sosinventory.sale;


import com.example.sosinventory.product.Product;
import com.example.sosinventory.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository salesRepository;
    private final ProductService productService;


    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @Override
    public String createSale(SaleRequest saleRequest) {

        List<Sale> salesList = new ArrayList<>();
        for (Item item : saleRequest.getItems()) {
            Product product = productService.getProductById(item.getProductId());
            Sale newSale = new Sale();
            newSale.setSaleDate(item.getSaleDate());
            newSale.setProduct(product);
            newSale.setQuantity(item.getQuantity());
            newSale.setTotalPrice(product.getPrice() * item.getQuantity());
            salesList.add(newSale);
        }
        salesRepository.saveAll(salesList);

        List<Product> productList = new ArrayList<>();
        //update each products quantity after sale
        for (Item item : saleRequest.getItems()) {
            Product product = productService.getProductById(item.getProductId());
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productList.add(product);
        }
        productService.saveAll(productList);

        //get invoice

        return buildInvoice(saleRequest);
    }


    @Override
    public List<Sale> getAllSales() {
        return salesRepository.findAll();
    }

    private String buildInvoice(SaleRequest saleRequest) {
//        StringBuilder sb = new StringBuilder();
//        for (Item item : saleRequest.getItems()) {
//            sb.append(item.)
//        }
        return "";
    }

}
