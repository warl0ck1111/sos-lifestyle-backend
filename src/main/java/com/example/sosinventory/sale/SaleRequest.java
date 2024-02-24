package com.example.sosinventory.sale;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class SaleRequest {

    @NotNull(message = "items is empty")
    private List<Item> items;
}
