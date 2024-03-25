package com.livelypuer.crud.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * Entity class for categories
 */
@Setter
@Getter
@Entity
@JsonSerialize
public class Category {

    /**
     * Unique identifier for the category
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Name of the category
     */
    private String title;

    /**
     * Indicates whether the category has been deleted or not
     */
    private Boolean deleted = false;

    /**
     * Date and time when the category was created
     */
    private Date createdDate = new Date(System.currentTimeMillis());

    /**
     * Date and time when the category was last updated
     */
    private Date updatedDate = new Date(System.currentTimeMillis());

    /**
     * Creates a new category with the specified title
     *
     * @param title title of the category
     */
    public Category(String title) {
        this.title = title;
    }

    /**
     * Creates a new category with an automatically generated ID and an empty title
     */
    public Category() {

    }
}
