package com.livelypuer.crud.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

/**
 * Entity class for products
 */
@Entity
@Setter
@Getter
@ToString
@JsonSerialize
@Table(name = "products")
public class Product {

    /**
     * Unique identifier for the product
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Title of the product
     */
    private String title;

    /**
     * Article number of the product
     */
    private String article;

    /**
     * Description of the product
     */
    private String description;

    /**
     * Category to which the product belongs
     */
    @ManyToOne()
    private Category category;

    /**
     * Price of the product
     */
    private Float price;

    /**
     * Quantity of the product in stock
     */
    private Integer quantity;

    /**
     * Date on which the product was created
     */
    private Date createdDate = new Date(System.currentTimeMillis());

    /**
     * Date on which the product was last updated
     */
    private Date updatedDate = new Date(System.currentTimeMillis());

    /**
     * Flag to indicate whether the product is deleted or not
     */
    private Boolean deleted = false;

    /**
     * Constructor to create a new product
     *
     * @param title       title of the product
     * @param article     article number of the product
     * @param description description of the product
     * @param category    category to which the product belongs
     * @param price       price of the product
     * @param quantity    quantity of the product in stock
     */
    public Product(String title, String article, String description, Category category, Float price, Integer quantity) {
        this.title = title;
        this.article = article;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Default constructor
     */
    public Product() {

    }
}
