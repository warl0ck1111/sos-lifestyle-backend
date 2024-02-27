package com.example.sosinventory.product;

import java.util.Collections;
import java.util.List;

public interface ProductService {

    Product createProduct(ProductRequest productRequest);

    Product updateProduct(long id, ProductRequest productRequest);

    List<Product> viewAllProducts();
    void deleteProduct(long id);

    Product getProductById(long id);
    Product save(Product product);
    void saveAll(List<Product> products);

    Product getProductByBarCode(String barCode);

    Product updateBrand(long productId, String brand);

    Product updateCategory(long productId, long categoryId);
    Product updateColor(long productId, String color);
    Product updateSize(long productId, Size size);

    Product getProductByName(String name);
}
