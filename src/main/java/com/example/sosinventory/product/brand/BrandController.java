package com.example.sosinventory.product.brand;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/brands")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable long id) {
        Brand brand = brandService.getBrandById(id);
        return ResponseEntity.ok(brand);
    }

    @PostMapping
    public ResponseEntity<Brand> createBrand(@RequestBody BrandRequest brandRequest) {
        Brand createdBrand = brandService.createBrand(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBrand);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable long id, @RequestBody BrandRequest brandRequest) {
        Brand updatedBrand = brandService.updateBrand(id, brandRequest);
        return ResponseEntity.ok(updatedBrand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}
