package com.livelypuer.crud.repositories;

import com.livelypuer.crud.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findAllByDeletedFalse();
    Optional<Category> findByIdAndDeletedFalse(UUID id);
}