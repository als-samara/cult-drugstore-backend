package com.generation.cultdrugstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.cultdrugstore.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	public List<Product> findAllByProductNameContainingIgnoreCase(String productName);
	public List<Product> findByPriceLessThan(double price);
	public List<Product> findByPriceGreaterThan(double price);
}
