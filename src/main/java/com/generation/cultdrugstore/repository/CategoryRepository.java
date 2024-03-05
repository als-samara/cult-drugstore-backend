package com.generation.cultdrugstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.cultdrugstore.model.Category;
import com.generation.cultdrugstore.model.Product;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	List<Category> findAllByDescriptionContainingIgnoreCase(String description);
	boolean existsByDescriptionIgnoreCase(String description);
	

	// @return A list of products associated with the category with the given description.
	@Query("SELECT p FROM Product p WHERE p.category.description = :description")
    List<Product> findProductsByCategoryDescription(@Param("description") String description);
}
