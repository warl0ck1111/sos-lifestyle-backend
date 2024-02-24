package com.example.sosinventory.product.brand;


import jakarta.persistence.*;
import lombok.Data;

@Data
public class BrandRequest {

    private String name;

    private String description;


}
