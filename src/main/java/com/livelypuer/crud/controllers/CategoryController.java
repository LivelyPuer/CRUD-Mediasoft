package com.livelypuer.crud.controllers;


import com.livelypuer.crud.entities.Category;
import com.livelypuer.crud.exeptions.NotFoundException;
import com.livelypuer.crud.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * This class is the REST controller for the Category entity. It provides endpoints for creating, reading, updating, and deleting categories.
 * It also handles exceptions thrown by the service layer.
 */
@RestController
@RequestMapping(path = "/category")
@Profile("production")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Creates a new CategoryController that uses the given CategoryService.
     *
     * @param categoryService the CategoryService to use
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Creates a new Category with the given title and returns its location.
     *
     * @param title the title of the new Category
     * @return a ResponseEntity with the created Category and the status code 201 (CREATED)
     */
    @PostMapping(path = "/create")
    public @ResponseBody ResponseEntity<Category> create(@RequestParam String title) {
        return ResponseEntity.created(categoryService.create(title)).build();
    }

    /**
     * Returns a Category with the given ID.
     *
     * @param id the ID of the Category to retrieve
     * @return the Category with the given ID
     * @throws NotFoundException if no Category with the given ID exists
     */
    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<Category> read(@PathVariable UUID id) throws NotFoundException {
        Category category = categoryService.get(id);
        if (category == null) {
            throw new NotFoundException("Category by Id " + id + " not found");
        }
        return ResponseEntity.ok(category);
    }

    /**
     * Returns all Categories.
     *
     * @return all Categories
     */
    @GetMapping(path = "/all")
    public @ResponseBody ResponseEntity<Iterable<Category>> all() {
        return ResponseEntity.ok(categoryService.list());
    }

    /**
     * Updates a Category with the given ID and title.
     *
     * @param id    the ID of the Category to update
     * @param title the new title of the Category
     * @return the updated Category
     */
    @PutMapping(path = "/update")
    public @ResponseBody ResponseEntity<Category> update(
            @RequestParam UUID id,
            @RequestParam(required = false) String title) {
        Category category = categoryService.update(id, title);
        return ResponseEntity.ok(category);
    }

    /**
     * Deletes a Category by its ID.
     *
     * @param id the ID of the Category to delete (set flag on database)
     * @return an empty response body
     */
    @DeleteMapping(path = "/delete")
    public @ResponseStatus ResponseEntity<Object> safeDelete(@RequestParam UUID id) {
        categoryService.safeDelete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes a Category permanently by its ID.
     *
     * @param id the ID of the Category to permanently delete
     * @return an empty response body
     */
    @DeleteMapping(path = "/permanent_delete")
    public @ResponseBody ResponseEntity<Object> delete(@RequestParam UUID id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Handles exceptions thrown by the service layer.
     *
     * @param e the exception thrown by the service layer
     * @return a ResponseEntity with the appropriate status code and message
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleException(EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    /**
     * Handles exceptions thrown by the service layer.
     *
     * @param e the exception thrown by the service layer
     * @return a ResponseEntity with the appropriate status code and message
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleException(NotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}