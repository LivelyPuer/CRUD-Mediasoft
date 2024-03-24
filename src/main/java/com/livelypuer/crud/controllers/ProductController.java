package com.livelypuer.crud.controllers;


import com.livelypuer.crud.entities.Category;
import com.livelypuer.crud.entities.Product;
import com.livelypuer.crud.repositories.CategoryRepository;
import com.livelypuer.crud.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @PostMapping(path = "/add")
    public @ResponseBody String addNewProduct(
            @RequestParam String title,
            @RequestParam String article,
            @RequestParam String description,
            @RequestParam UUID category_id,
            @RequestParam Float price,
            @RequestParam Integer quantity
    ) {
        Category category = categoryRepository.getReferenceById(category_id);
        Date created = new Date(System.currentTimeMillis());
        Date updated = new Date(System.currentTimeMillis());
        Product product = new Product(title, article, description, category, price, quantity, created, updated);
        Product _product = productRepository.save(product);
        return String.valueOf(new ResponseEntity<>(_product, HttpStatus.CREATED));
    }

    @RequestMapping(path = "/all")
    public @ResponseBody Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }
}