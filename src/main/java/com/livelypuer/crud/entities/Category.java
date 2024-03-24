package com.livelypuer.crud.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@Entity
@JsonSerialize
public class Category {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private Boolean deleted = false;
    private Date createdDate = new Date(System.currentTimeMillis());
    private Date updatedDate = new Date(System.currentTimeMillis());

    public Category(String title) {
        this.title = title;
    }

    public Category() {

    }
}
