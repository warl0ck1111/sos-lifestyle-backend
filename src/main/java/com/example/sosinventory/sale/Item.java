package com.example.sosinventory.sale;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Item {

    private long productId;
    private String barCodeNumber;
    private int quantity;
    private LocalDateTime saleDate;

}
