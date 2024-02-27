package com.example.sosinventory.product;

import com.example.sosinventory.product.brand.Brand;
import com.example.sosinventory.product.category.Category;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Column(nullable = false, unique = true)
    private String barCode;


    private double price;

    private int quantity;

    @ManyToOne
    private Category category;

    @Column
    private String brand;

    private String color;

    @Enumerated(EnumType.STRING)
    private Size size;

    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd h:m:s")
    private LocalDateTime timeCreated;

    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd h:m:s")
    private LocalDateTime timeUpdated;





}
