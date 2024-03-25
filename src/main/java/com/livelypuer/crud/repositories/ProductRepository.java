package com.livelypuer.crud.repositories;

import com.livelypuer.crud.entities.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The {@code ProductRepository} interface provides methods for interacting with the database and retrieving {@link Product} entities.
 * This interface extends the Spring Data JPA repository interface, which provides standard methods for creating, reading, updating, and deleting entities.
 * The repository is annotated with the {@code @Repository} annotation, which indicates that it is a component that can be managed by the Spring container.
 * The {@code @Profile("production")} annotation is used to indicate that this repository is only active when the "production" profile is active.
 * This ensures that the repository is not used in development or testing environments.
 */
@Repository
@Profile("production")
public interface ProductRepository extends JpaRepository<Product, UUID> {

    /**
     * Returns a list of all products that have been marked as "deleted" field is set to false.
     *
     * @return a list of products
     */
    List<Product> findAllByDeletedFalse();

    /**
     * Returns an optional product with the specified ID, where the "deleted" field is set to false.
     *
     * @param id the ID of the product to retrieve
     * @return an optional product
     */
    Optional<Product> findByIdAndDeletedFalse(UUID id);

}