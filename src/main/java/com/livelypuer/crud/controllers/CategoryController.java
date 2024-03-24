package com.livelypuer.crud.controllers;


import com.livelypuer.crud.entities.Category;
import com.livelypuer.crud.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping(path="")
    public String empty() {
        return "category";
    }
    @PostMapping(path="/add")
    public @ResponseBody String addNewCategory (@RequestParam String title) {


        Category n = new Category();
        n.setTitle(title);
        categoryRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}