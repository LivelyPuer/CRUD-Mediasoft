package com.livelypuer.crud.repositories;

import com.livelypuer.crud.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>{
    List<Product> findAllByDeletedFalse();
    Optional<Product> findByIdAndDeletedFalse(UUID id);
}