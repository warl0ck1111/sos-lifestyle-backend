package com.example.sosinventory.product.color;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "colors")
@Data
public class Color {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;


}
