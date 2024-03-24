package com.livelypuer.crud.services;


import com.livelypuer.crud.entities.Category;
import com.livelypuer.crud.entities.Product;
import com.livelypuer.crud.repositories.CategoryRepository;
import com.livelypuer.crud.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> list() {
        return productRepository.findAllByDeletedFalse();
    }

    public URI create(String title, String article, String description, UUID category_id, Float price, Integer quantity) {
        Optional<Category> optionalCategory = categoryRepository.findByIdAndDeletedFalse(category_id);
        Category category = optionalCategory.orElse(null);
        if (category == null) {
            throw new EntityNotFoundException("Category by id %s not founded".formatted(category_id));
        }
        Product product = new Product(title, article, description, category, price, quantity);
        return URI.create("/product/%s".formatted(productRepository.save(product).getId()));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product get(UUID id) {
        Optional<Product> optionalProduct = productRepository.findByIdAndDeletedFalse(id);
        return optionalProduct.orElse(null);
    }

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
            if (category == null){
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

    public void safeDelete(UUID id) {
        Product product = get(id);
        if (product == null) {
            return;
        }
        product.setDeleted(true);
        product.setUpdatedDate(new Date(System.currentTimeMillis()));
        productRepository.save(product);
    }

    public void delete(UUID id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = optionalProduct.orElse(null);
        if (product == null) {
            return;
        }
        productRepository.deleteById(id);
    }
}