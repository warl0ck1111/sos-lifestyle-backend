package com.example.sosinventory.product.brand;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;


    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrandById(long id) {
        return findBrandById(id);
    }

    @Override
    public Brand createBrand(BrandRequest brandRequest) {

        log.info("createBrand/brandRequest:{}", brandRequest);
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandRequest,brand);
        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(long id, BrandRequest brandRequest) {
        Brand brand = findBrandById(id);
        BeanUtils.copyProperties(brandRequest, brand);
        return brandRepository.save(brand);
    }

    @Override
    public void deleteBrand(long id) {
        brandRepository.deleteById(id);
    }



    private Brand findBrandById(long id){
        return brandRepository.findById(id).orElseThrow(()-> new BrandException("brand not found"));
    }


}
