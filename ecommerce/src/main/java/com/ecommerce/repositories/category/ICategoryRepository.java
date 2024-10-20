package com.ecommerce.repositories.category;

import com.ecommerce.entities.category.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryModel, String> {
    Optional<CategoryModel> findByName(String name);
}
