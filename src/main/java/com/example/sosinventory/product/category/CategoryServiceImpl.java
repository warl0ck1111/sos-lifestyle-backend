package com.example.sosinventory.product.category;



import com.example.sosinventory.product.category.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public List<Category> getAllCategorys() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(long id) {
        return findCategoryById(id);
    }

    @Override
    public Category createCategory(CategoryRequest categoryRequest) {

        log.info("createCategory/categoryRequest:{}", categoryRequest);
        Category category = new Category();
        BeanUtils.copyProperties(categoryRequest,category);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(long id, CategoryRequest categoryRequest) {
        Category category = findCategoryById(id);
        BeanUtils.copyProperties(categoryRequest, category);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }



    private Category findCategoryById(long id){
        return categoryRepository.findById(id).orElseThrow(()-> new CategoryException("band not found"));
    }


}
