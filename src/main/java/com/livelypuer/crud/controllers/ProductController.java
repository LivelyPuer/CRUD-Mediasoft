package com.livelypuer.crud.controllers;


import com.livelypuer.crud.entities.Category;
import com.livelypuer.crud.entities.Product;
import com.livelypuer.crud.repositories.CategoryRepository;
import com.livelypuer.crud.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping(path = "/create", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Product> create(
            @RequestParam String title,
            @RequestParam String article,
            @RequestParam String description,
            @RequestParam UUID category_id,
            @RequestParam Float price,
            @RequestParam Integer quantity
    ) {

        Category category = categoryRepository.getReferenceById(category_id);
        Product product = new Product(title, article, description, category, price, quantity);
        Product _product = productRepository.save(product);
        return ResponseEntity.created(URI.create("/product/%s".formatted(_product.getId()))).build();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<Product> read(@PathVariable UUID id) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            Product product = optionalProduct.orElse(null);
            if (product == null) {
                throw new EntityNotFoundException("Product by id %s not founded".formatted(id));
            }
            return new ResponseEntity<>(productRepository.getReferenceById(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Product> getAllProducts() {
        return productRepository.findAllByDeletedFalse();
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
            Optional<Product> optionalProduct = productRepository.findById(id);
            Product product = optionalProduct.orElse(null);
            if (product == null) {
                throw new EntityNotFoundException("Product by id %s not founded".formatted(id));
            }
            if (category_id != null) {
                Optional<Category> optionalCategory = categoryRepository.findById(category_id);
                Category category = optionalCategory.orElse(null);
                if (category == null) {
                    throw new EntityNotFoundException("Category by id %s not founded".formatted(category_id));
                }
                product.setCategory(category);
            }
            if (title != null) {
                product.setTitle(title);
            }
            if (article != null) {
                product.setArticle(article);
            }
            if (description != null) {
                product.setDescription(description);
            }
            if (price != null) {
                product.setPrice(price);
            }
            if (quantity != null) {
                product.setQuantity(quantity);
            }
            product.setUpdatedDate(new Date(System.currentTimeMillis()));
            productRepository.save(product);
            return ResponseEntity.ok(product);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }


    @DeleteMapping(path = "/delete")
    public @ResponseStatus ResponseEntity<Object> safeDelete(@RequestParam UUID id) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            Product product = optionalProduct.orElse(null);
            if (product == null || product.getDeleted()) {
                throw new EntityNotFoundException("Product by id %s not founded".formatted(id));
            }
            product.setDeleted(true);
            productRepository.save(product);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping(path = "/permanent_delete")
    public @ResponseBody ResponseEntity<Object> delete(@RequestParam UUID id) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            Product product = optionalProduct.orElse(null);
            if (product == null) {
                throw new EntityNotFoundException("Product by id %s not founded".formatted(id));
            }
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }

}