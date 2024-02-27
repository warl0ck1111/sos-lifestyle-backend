package com.example.sosinventory.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)

public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest) {
        Product createdProduct = productService.createProduct(productRequest);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody ProductRequest productRequest) {
        Product updatedProduct = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> viewAllProducts() {
        List<Product> products = productService.viewAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<Product> getProductByBarCode(@PathVariable String barcode) {
        Product product = productService.getProductByBarCode(barcode);
        //todo if product quantity is 0 h
        return ResponseEntity.ok(product);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        Product product = productService.getProductByName(name);
        //todo if product quantity is 0 h
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{productId}/brand/{brandId}")
    public ResponseEntity<Product> updateBrand(@PathVariable long productId, @PathVariable String brandId) {
        Product product = productService.updateBrand(productId, brandId);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<Product> updateCategory(@PathVariable long productId, @PathVariable long categoryId) {
        Product product = productService.updateCategory(productId, categoryId);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{productId}/size/{size}")
    public ResponseEntity<Product> updateSize(@PathVariable long productId, @PathVariable Size size) {
        Product product = productService.updateSize(productId, size);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{productId}/color/{color}")
    public ResponseEntity<Product> updateColor(@PathVariable long productId, @PathVariable String color) {
        Product product = productService.updateColor(productId, color);
        return ResponseEntity.ok(product);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }



}
