package com.livelypuer.crud.services;

import com.livelypuer.crud.entities.Category;
import com.livelypuer.crud.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> list() {
        return categoryRepository.findAllByDeletedFalse();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }
    public URI create(String title) {
        Category category = new Category(title);
        return URI.create("/product/%s".formatted(categoryRepository.save(category).getId()));
    }
    public Category get(UUID id) {
        Optional<Category> optionalCategory = categoryRepository.findByIdAndDeletedFalse(id);
        return optionalCategory.orElse(null);
    }

    public Category update(UUID id, String title) {
        Category category = get(id);
        if (category == null) {
            throw new EntityNotFoundException("Category by id %s not founded".formatted(id));
        }
        if (title != null) {
            category.setTitle(title);
        }
        category.setUpdatedDate(new Date(System.currentTimeMillis()));
        categoryRepository.save(category);
        return category;
    }
    public void safeDelete(UUID id) {
        Category category = get(id);
        if (category == null) {
            return;
        }
        category.setDeleted(true);
        category.setUpdatedDate(new Date(System.currentTimeMillis()));
        categoryRepository.save(category);
    }
    public void delete(UUID id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        Category category = optionalCategory.orElse(null);
        if (category == null) {
            return;
        }
        categoryRepository.deleteById(id);
    }
}