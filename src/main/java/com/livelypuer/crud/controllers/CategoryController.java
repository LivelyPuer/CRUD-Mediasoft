package com.livelypuer.crud.controllers;


import com.livelypuer.crud.entities.Category;
import com.livelypuer.crud.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping(path = "/create")
    public @ResponseBody ResponseEntity<Category> create(@RequestParam String title) {
        return ResponseEntity.created(categoryService.create(title)).build();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<Category> read(@PathVariable UUID id) {
        Category category = categoryService.get(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);

    }

    @GetMapping(path = "/all")
    public @ResponseBody ResponseEntity<Iterable<Category>> all() {
        return ResponseEntity.ok(categoryService.list());
    }

    @PutMapping(path = "/update")
    public @ResponseBody ResponseEntity<Category> update(
            @RequestParam UUID id,
            @RequestParam(required = false) String title) {
        try {
            Category category = categoryService.update(id, title);
            return ResponseEntity.ok(category);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "/delete")
    public @ResponseStatus ResponseEntity<Object> safeDelete(@RequestParam UUID id) {
        categoryService.safeDelete(id);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping(path = "/permanent_delete")
    public @ResponseBody ResponseEntity<Object> delete(@RequestParam UUID id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}