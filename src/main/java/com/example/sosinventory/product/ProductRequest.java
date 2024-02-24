package com.example.sosinventory.product;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductRequest {

    @NotBlank(message = "product name can not be empty")
    private String name;

    private String description;

    @NotBlank(message = "product barCode can not be empty")
    private String barCode;

    @Min(value =1, message = "invalid product price")
    private double price;

    @Min(value =1, message = "invalid product quantity")
    private int quantity;


    @Min(value = 1, message = "invalid product categoryId")
    private long categoryId;

    @Min(value = 1, message = "invalid product brandId")
    private long brandId;

    private String color;

    @Enumerated(EnumType.STRING)
    private Size size;
}
