package com.livelypuer.crud.repositories;

import com.livelypuer.crud.entities.Category;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The {@code CategoryRepository} interface provides methods for interacting with the database table that stores {@link Category} entities.
 * This interface extends the Spring Data JPA repository interface, which provides standard methods for performing create, read, update, and delete (CRUD) operations on the database.
 * The repository is annotated with the {@code @Repository} annotation, which indicates that it is a component that can be managed by the Spring container.
 * The {@code @Profile("production")} annotation is used to indicate that this repository is only active when the application is running in the "production" profile.
 */
@Repository
@Profile("production")
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    /**
     * Returns a list of all categories that have been marked as not deleted.
     *
     * @return a list of all categories that have been marked as not deleted
     */
    List<Category> findAllByDeletedFalse();

    /**
     * Returns an optional category with the specified ID, if it exists and has not been marked as deleted.
     *
     * @param id the ID of the category to retrieve
     * @return the optional category with the specified ID
     */
    Optional<Category> findByIdAndDeletedFalse(UUID id);

}