package com.livelypuer.crud.services;


import com.livelypuer.crud.entities.Category;
import com.livelypuer.crud.entities.Product;
import com.livelypuer.crud.repositories.CategoryRepository;
import com.livelypuer.crud.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The ProductService class provides business logic for managing products.
 */
@Service
@Profile("production")
public class ProductService {
    /**
     * The productRepository field is used to access the product data store.
     */
    @Autowired
    private ProductRepository productRepository;
    /**
     * The categoryRepository field is used to access the category data store.
     */
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Returns a list of all products that have not been marked as deleted.
     *
     * @return a list of products
     */
    public List<Product> list() {
        return productRepository.findAllByDeletedFalse();
    }

    /**
     * Creates a new product and saves it to the data store.
     *
     * @param title       the product title
     * @param article     the product article
     * @param description the product description
     * @param category_id the ID of the product category
     * @param price       the product price
     * @param quantity    the product quantity
     * @return the URI of the created product
     * @throws EntityNotFoundException if the specified category ID does not exist
     */
    public URI create(String title, String article, String description, UUID category_id, Float price, Integer quantity) {
        Optional<Category> optionalCategory = categoryRepository.findByIdAndDeletedFalse(category_id);
        Category category = optionalCategory.orElse(null);
        if (category == null) {
            throw new EntityNotFoundException("Category by id %s not founded".formatted(category_id));
        }
        Product product = new Product(title, article, description, category, price, quantity);
        return URI.create("/product/%s".formatted(productRepository.save(product).getId()));
    }

    /**
     * Saves an existing product to the data store.
     *
     * @param product the product to save
     * @return the saved product
     */
    public Product save(Product product) {
        return productRepository.save(product);
    }

    /**
     * Returns a product with the specified ID, or null if the product does not exist.
     *
     * @param id the ID of the product to retrieve
     * @return the product, or null
     */
    public Product get(UUID id) {
        Optional<Product> optionalProduct = productRepository.findByIdAndDeletedFalse(id);
        return optionalProduct.orElse(null);
    }

    /**
     * Updates an existing product in the data store.
     *
     * @param id          the ID of the product to update
     * @param title       the new title for the product
     * @param article     the new article for the product
     * @param description the new description for the product
     * @param category_id the ID of the new category for the product
     * @param price       the new price for the product
     * @param quantity    the new quantity for the product
     * @return the updated product
     * @throws EntityNotFoundException if the specified category ID does not exist
     */
    public Product update(UUID id,
                          String title,
                          String article,
                          String description,
                          UUID category_id,
                          Float price,
                          Integer quantity) {
        Product product = get(id);
        if (category_id != null) {
            Optional<Category> optionalCategory = categoryRepository.findByIdAndDeletedFalse(category_id);
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
        return product;
    }

    /**
     * Marks a product as deleted in the data store.
     *
     * @param id the ID of the product to delete
     */
    public void safeDelete(UUID id) {
        Product product = get(id);
        if (product == null) {
            return;
        }
        product.setDeleted(true);
        product.setUpdatedDate(new Date(System.currentTimeMillis()));
        productRepository.save(product);
    }

    /**
     * Permanently deletes a product from the data store.
     *
     * @param id the ID of the product to delete
     */
    public void delete(UUID id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = optionalProduct.orElse(null);
        if (product == null) {
            return;
        }
        productRepository.deleteById(id);
    }
}