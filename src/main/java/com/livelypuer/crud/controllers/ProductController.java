package com.livelypuer.crud.controllers;


import com.livelypuer.crud.entities.Product;
import com.livelypuer.crud.exeptions.NotFoundException;
import com.livelypuer.crud.services.ProductService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * This class is the REST controller for the Product entity. It provides endpoints for creating, reading, updating, and deleting categories.
 * It also handles exceptions thrown by the service layer.
 */
@RestController
@RequestMapping(path = "/product")
@Profile("production")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Creates a new product with the given details.
     *
     * @param title       the title of the product
     * @param article     the article number of the product
     * @param description the description of the product
     * @param category_id the ID of the category to which the product belongs
     * @param price       the price of the product
     * @param quantity    the quantity of the product in stock
     * @return a response indicating whether the creation was successful, along with the created product
     */
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
        return ResponseEntity.created(productService.create(title, article, description, category_id, price, quantity)).build();
    }

    /**
     * Returns a product with the specified ID.
     *
     * @param id the ID of the product to retrieve
     * @return a ResponseEntity with the product, or a Not Found error if the product does not exist
     * @throws NotFoundException if the product does not exist
     */
    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<Product> read(@PathVariable UUID id) throws NotFoundException {
        Product product = productService.get(id);
        if (product == null) {
            throw new NotFoundException("Product by Id %s not founded".formatted(id));

        }
        return ResponseEntity.ok(product);
    }

    /**
     * Returns a list of all products.
     *
     * @return a list of all products
     */
    @GetMapping(path = "/all")
    public @ResponseBody ResponseEntity<Iterable<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.list());
    }

    /**
     * Updates an existing product with the given details.
     *
     * @param id          the ID of the product to update
     * @param title       the title of the product
     * @param article     the article number of the product
     * @param description the description of the product
     * @param category_id the ID of the category to which the product belongs
     * @param price       the price of the product
     * @param quantity    the quantity of the product in stock
     * @return the updated product
     */
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
        Product product = productService.update(id, title, article, description, category_id, price, quantity);
        return ResponseEntity.ok(product);
    }

    /**
     * Deletes the product with the specified ID, but only if it is not referenced by any other entities.
     *
     * @param id the ID of the product to delete
     * @return a response indicating whether the deletion was successful
     */
    @DeleteMapping(path = "/delete")
    public @ResponseStatus ResponseEntity<Object> safeDelete(@RequestParam UUID id) {
        productService.safeDelete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes the product with the specified ID, but only if it is not referenced by any other entities.
     *
     * @param id the ID of the product to delete
     * @return a response indicating whether the deletion was successful
     */
    @DeleteMapping(path = "/permanent_delete")
    public @ResponseBody ResponseEntity<Object> delete(@RequestParam UUID id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

//    /**
//     * This method handles the EntityNotFoundException exception, which is thrown when an entity cannot be found in the database.
//     *
//     * @param e the EntityNotFoundException exception that was thrown
//     * @return a ResponseEntity with a status code of 404 (Not Found)
//     */
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<?> handleException(EntityNotFoundException e) {
//        return ResponseEntity.notFound().build();
//    }
//
//    /**
//     * This method handles the EntityNotFoundException exception, which is thrown when an entity cannot be found in the database.
//     *
//     * @param e the EntityNotFoundException exception that was thrown
//     * @return a ResponseEntity with a status code of 404 (Not Found)
//     */
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<?> handleException(NotFoundException e) {
//        return ResponseEntity.notFound().build();
//    }
}