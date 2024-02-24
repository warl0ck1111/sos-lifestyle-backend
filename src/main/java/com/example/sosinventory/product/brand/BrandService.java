package com.example.sosinventory.product.brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    List<Brand> getAllBrands();

    Brand getBrandById(long id);

    Brand createBrand(BrandRequest brandRequest);

    Brand updateBrand(long id, BrandRequest brandRequest);

    void deleteBrand(long id);
}
