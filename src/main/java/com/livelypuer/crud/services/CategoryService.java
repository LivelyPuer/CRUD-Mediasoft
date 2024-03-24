package com.livelypuer.crud.services;

import com.livelypuer.crud.entities.Category;
import com.livelypuer.crud.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> list() {
        return categoryRepository.findAll();
    }
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}