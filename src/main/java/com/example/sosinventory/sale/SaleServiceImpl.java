package com.example.sosinventory.sale;


import com.example.sosinventory.product.Product;
import com.example.sosinventory.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository salesRepository;
    private final ProductService productService;


    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @Override
    public String createSale(SaleRequest saleRequest) {
        String invoiceNumber = generateInvoiceNumber();
        List<Sale> salesList = new ArrayList<>();
        for (Item item : saleRequest.getItems()) {
            Product product = productService.getProductById(item.getProductId());
            Sale newSale = new Sale();
            newSale.setSaleDate(LocalDateTime.now());
            newSale.setProduct(product);
            newSale.setInvoiceNo(invoiceNumber);
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
        return salesRepository.findAll(Sort.by(Sort.Direction.DESC, "timeUpdated"));
    }

    private String buildInvoice(SaleRequest saleRequest) {
//        StringBuilder sb = new StringBuilder();
//        for (Item item : saleRequest.getItems()) {
//            sb.append(item.)
//        }
        return "";
    }



    public static String generateInvoiceNumber() {
        return "SOS-" + LocalDate.now().getDayOfMonth()+ LocalDateTime.now().getHour()+ LocalDateTime.now().getMinute(); // Custom format, e.g., INV-1001, INV-1002, ...
    }

}
