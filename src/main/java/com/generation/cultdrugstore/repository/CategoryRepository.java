package com.generation.cultdrugstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.cultdrugstore.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	List<Category> findAllByDescriptionContainingIgnoreCase(String description);
	boolean existsByDescriptionIgnoreCase(String description);
}
