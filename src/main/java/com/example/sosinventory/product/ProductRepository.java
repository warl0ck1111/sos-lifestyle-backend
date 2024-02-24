package com.example.sosinventory.product;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByBarCodeIgnoreCase(String barCode);

    boolean existsByBarCodeIgnoreCase(String barCode);

    boolean existsByNameIgnoreCase(String name);
}
