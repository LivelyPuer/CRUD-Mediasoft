package com.livelypuer.crud.controllers;


import com.livelypuer.crud.entities.Product;
import com.livelypuer.crud.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping(path = "/create", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Product> create(
            @RequestParam String title,
            @RequestParam String article,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) UUID category_id,
            @RequestParam Float price,
            @RequestParam Integer quantity
    ) {
        try{
            return ResponseEntity.created(productService.create(title, article, description, category_id, price, quantity)).build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<Product> read(@PathVariable UUID id) {
        Product product = productService.get(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping(path = "/all")
    public @ResponseBody ResponseEntity<Iterable<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.list());
    }

    @PutMapping(path = "/update")
    public @ResponseBody ResponseEntity<Product> update(
            @RequestParam UUID id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String article,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) UUID category_id,
            @RequestParam(required = false) Float price,
            @RequestParam(required = false) Integer quantity
    ) {
        try {
            Product product = productService.update(id, title, article, description, category_id, price, quantity);
            return ResponseEntity.ok(product);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping(path = "/delete")
    public @ResponseStatus ResponseEntity<Object> safeDelete(@RequestParam UUID id) {
        productService.safeDelete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/permanent_delete")
    public @ResponseBody ResponseEntity<Object> delete(@RequestParam UUID id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}