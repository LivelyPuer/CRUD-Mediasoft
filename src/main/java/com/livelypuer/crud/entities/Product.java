package com.livelypuer.crud.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Entity
@Setter
@Getter
@ToString
@JsonSerialize
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
    private Date createdDate = new Date(System.currentTimeMillis());
    private Date updatedDate = new Date(System.currentTimeMillis());
    private Boolean deleted = false;

    public Product(String title, String article, String description, Category category, Float price, Integer quantity) {
        this.title = title;
        this.article = article;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public Product() {

    }
}
