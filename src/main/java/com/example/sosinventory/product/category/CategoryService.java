package com.example.sosinventory.product.category;

import com.example.sosinventory.product.category.Category;
import com.example.sosinventory.product.category.CategoryRequest;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategorys();

    Category getCategoryById(long id);

    Category createCategory(CategoryRequest categoryRequest);

    Category updateCategory(long id, CategoryRequest categoryRequest);

    void deleteCategory(long id);
}
