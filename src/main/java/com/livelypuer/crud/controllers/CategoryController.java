package com.livelypuer.crud.controllers;


import com.livelypuer.crud.entities.Category;
import com.livelypuer.crud.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping(path = "/create")
    public @ResponseBody ResponseEntity<Category> create(@RequestParam String title) {
        Category category = new Category(title);
        Category _category = categoryRepository.save(category);
        return ResponseEntity.created(URI.create("/product/%s".formatted(_category.getId()))).build();
    }
    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<Category> read(@PathVariable UUID id) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            Category category = optionalCategory.orElse(null);
            if (category == null) {
                throw new EntityNotFoundException("Category by id %s not founded".formatted(id));
            }
            return new ResponseEntity<>(categoryRepository.getReferenceById(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Category> all() {
        return categoryRepository.findAllByDeletedFalse();
    }

    @PutMapping(path = "/update")
    public @ResponseBody ResponseEntity<Category> update(
            @RequestParam UUID id,
            @RequestParam(required = false) String title) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            Category category = optionalCategory.orElse(null);
            if (category == null) {
                throw new EntityNotFoundException("Category by id %s not founded".formatted(id));
            }
            if (title != null) {
                category.setTitle(title);
            }
            category.setUpdatedDate(new Date(System.currentTimeMillis()));
            categoryRepository.save(category);
            return ResponseEntity.ok(category);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping(path = "/delete")
    public @ResponseStatus ResponseEntity<Object> safeDelete(@RequestParam UUID id) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            Category category = optionalCategory.orElse(null);
            if (category == null || category.getDeleted()) {
                throw new EntityNotFoundException("Product by id %s not founded".formatted(id));
            }
            category.setDeleted(true);
            categoryRepository.save(category);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping(path = "/permanent_delete")
    public @ResponseBody ResponseEntity<Object> delete(@RequestParam UUID id) {
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            Category category = categoryOptional.orElse(null);
            if (category == null) {
                throw new EntityNotFoundException("Product by id %s not founded".formatted(id));
            }
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }
}