package com.livelypuer.crud.services;

import com.livelypuer.crud.entities.Category;
import com.livelypuer.crud.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * Service class for managing categories.
 */
@Service
@Profile("production")
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Creates a new instance of the CategoryService class.
     *
     * @param categoryRepository the repository for accessing the category data
     */
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Returns a list of all categories.
     *
     * @return a list of all categories
     */
    public List<Category> list() {
        return categoryRepository.findAllByDeletedFalse();
    }

    /**
     * Saves a new category.
     *
     * @param category the category to save
     * @return the saved category
     */
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Creates a new category and returns its URI.
     *
     * @param title the title of the new category
     * @return the URI of the new category
     */
    public URI create(String title) {
        Category category = new Category(title);
        return URI.create("/product/" + categoryRepository.save(category).getId());
    }

    /**
     * Returns a category by its ID.
     *
     * @param id the ID of the category to retrieve
     * @return the category with the specified ID, or null if no category with that ID exists
     */
    public Category get(UUID id) {
        Optional<Category> optionalCategory = categoryRepository.findByIdAndDeletedFalse(id);
        return optionalCategory.orElse(null);
    }

    /**
     * Updates an existing category.
     *
     * @param id      the ID of the category to update
     * @param title   the new title of the category, or null to leave the title unchanged
     * @return the updated category
     */
    public Category update(UUID id, String title) {
        Category category = get(id);
        if (category == null) {
            throw new EntityNotFoundException("Category by id " + id + " not found");
        }
        if (title!= null) {
            category.setTitle(title);
        }
        category.setUpdatedDate(new Date(System.currentTimeMillis()));
        categoryRepository.save(category);
        return category;
    }

    /**
     * Safely deletes a category by setting its deleted flag to true.
     *
     * @param id the ID of the category to delete
     */
    public void safeDelete(UUID id) {
        Category category = get(id);
        if (category == null) {
            return;
        }
        category.setDeleted(true);
        category.setUpdatedDate(new Date(System.currentTimeMillis()));
        categoryRepository.save(category);
    }

    /**
     * Permanently deletes a category.
     *
     * @param id the ID of the category to delete
     */
    public void delete(UUID id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        Category category = optionalCategory.orElse(null);
        if (category == null) {
            return;
        }
        categoryRepository.deleteById(id);
    }
}