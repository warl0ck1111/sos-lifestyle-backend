package com.example.sosinventory.product;

import com.example.sosinventory.product.category.Category;
import com.example.sosinventory.product.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class productServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @Override
    public Product createProduct(ProductRequest productRequest) {
        log.info("Creating product: {}", productRequest);
        if (productDoesNotExist(productRequest)) {
            Product product = new Product();
            BeanUtils.copyProperties(productRequest, product);
            Category category = categoryService.getCategoryById(productRequest.getCategoryId());
//            Brand brand = brandService.getBrandById(productRequest.getBrandId());
            product.setBrand(productRequest.getBrand());
            product.setCategory(category);
            return productRepository.save(product);
        } else {
            return findProductByBarCode(productRequest.getBarCode());
        }
    }

    private boolean productDoesNotExist(ProductRequest productRequest) {
        if (!productRepository.existsByNameIgnoreCase(productRequest.getName())) {
            if (!productRepository.existsByBarCodeIgnoreCase(productRequest.getBarCode())) {
                return true;
            } else {
                log.info("product already exists, updatig it instead");
            }
        } else {
            log.info("product already exists. updating it instead");
        }
        return false;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @Override
    public Product updateProduct(long id, ProductRequest productRequest) {
        log.info("Updating product with ID {}: {}", id, productRequest);

            Product product = findProductById(id);
            BeanUtils.copyProperties(productRequest,product);
            return productRepository.save(product);
    }

    @Override
    public List<Product> viewAllProducts() {
        log.info("Viewing all products");
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "timeUpdated"));
    }

    @Override
    public void deleteProduct(long id) {
        log.info("Deleting product with ID: {}", id);
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(long id) {
        log.info("getProductById/id:{}", id);
        return findProductById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void saveAll(List<Product> productList) {
        productRepository.saveAll(productList);
    }

    @Override
    public Product getProductByBarCode(String barCode) {
        log.info("getProductByBarCode/barCode:{}", barCode);
        return findProductByBarCode(barCode);
    }

    @Override
    public Product getProductByName(String name) {
        log.info("getProductByName/name:{}", name);
        return findProductByName(name);
    }


    @Override
    public Product updateBrand(long productId, String brand) {
        log.info("updateBrand/productId:{}", productId);
        log.info("updateBrand/brand:{}", brand);
        Product product = findProductById(productId);
//        Brand brand = brandService.getBrandById(brand);
        product.setBrand(brand);
        return productRepository.save(product);
    }

    @Override
    public Product updateCategory(long productId, long categoryId) {
        log.info("updateCategory/productId:{}", productId);
        log.info("updateCategory/categoryId:{}", categoryId);
        Product product = findProductById(productId);
        Category category = categoryService.getCategoryById(categoryId);
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Product updateColor(long productId, String color) {
        log.info("updateColor/productId:{}", productId);
        log.info("updateColor/color:{}", color);
        Product product = findProductById(productId);
        product.setColor(color);
        return productRepository.save(product);
    }

    @Override
    public Product updateSize(long productId, Size size) {
        log.info("updateColor/productId:{}", productId);
        log.info("updateColor/color:{}", size);
        Product product = findProductById(productId);
        product.setSize(size);
        return productRepository.save(product);
    }


    private Product findProductById(long id) {
        log.info("findProductById/id:{}", id);
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("product with id:" + id + " not found"));
    }

    private Product findProductByBarCode(String barCode) {
        log.info("findProductByBarCode/barCode:{}", barCode);
        return productRepository.findByBarCodeIgnoreCase(barCode).orElseThrow(() -> new ProductNotFoundException("product with barcode:" + barCode + " not found"));
    }

    private Product findProductByName(String name) {
        log.info("findProductByName/name:{}", name);
        return productRepository.findByNameIgnoreCase(name).orElseThrow(() -> new ProductNotFoundException("product with name:" + name + " not found"));
    }
}
