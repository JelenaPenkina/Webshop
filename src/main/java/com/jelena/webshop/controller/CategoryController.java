package com.jelena.webshop.controller;

import com.jelena.webshop.entity.Category;
import com.jelena.webshop.entity.Product;
import com.jelena.webshop.repository.CategoryRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    CategoryRepository categoryRepository;

    @PostMapping("category/add")
    public List<Category> addCategory(@RequestBody Category category) {
        categoryRepository.save(category);
        return categoryRepository.findAll();
    }
    // PUT localhost:8080/product
    @PutMapping("category")
    public List<Category> editCategory(@RequestBody Category category) {
        if(categoryRepository.existsById(category.getId())) {
            categoryRepository.save(category);
        }
        return categoryRepository.findAll();
    }
}
