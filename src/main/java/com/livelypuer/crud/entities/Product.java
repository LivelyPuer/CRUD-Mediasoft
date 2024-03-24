package com.livelypuer.crud.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;
@Entity
@Setter
@Getter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String article;
    private String description;
    @ManyToOne()
    private Category category;
    private Float price;
    private Integer quantity;
    private Date createdDate;
    private Date updatedDate;

    public Product(String title, String article, String description, Category category, Float price, Integer quantity, Date createdDate, Date updatedDate) {
        this.title = title;
        this.article = article;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Product() {

    }
}
