package com.example.sosinventory.sale.invoice;

import com.example.sosinventory.product.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany
    private List<Product> product;
    private double quantity;
    private double totalPrice;
    private LocalDateTime saleDateTime;



}
