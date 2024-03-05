package com.generation.cultdrugstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.cultdrugstore.dto.ProductWithCategoryDTO;
import com.generation.cultdrugstore.model.Product;
import com.generation.cultdrugstore.repository.CategoryRepository;
import com.generation.cultdrugstore.repository.ProductRepository;
import com.generation.cultdrugstore.service.ProductService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired 
	CategoryRepository categoryRepository;
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/all")
	public ResponseEntity<List<ProductWithCategoryDTO>> getAll(){
		return ResponseEntity.ok(productService.getAll());
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ProductWithCategoryDTO> getById(@PathVariable Long id){
		return ResponseEntity.ok(productService.getById(id));
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<ProductWithCategoryDTO>> getByName(@PathVariable String name) {
		return ResponseEntity.ok(productService.getByName(name));
	}
	
	@PostMapping("/create")
	public ResponseEntity<ProductWithCategoryDTO> post(@Valid @RequestBody ProductWithCategoryDTO product) {
		return ResponseEntity.ok(productService.create(product));
	}
	
	@PutMapping("/update")
	public ResponseEntity<ProductWithCategoryDTO> put(@Valid @RequestBody ProductWithCategoryDTO product) {
		return ResponseEntity.ok(productService.update(product));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		productService.delete(id);
        return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/lessthan/{price}")
	public ResponseEntity<List<ProductWithCategoryDTO>> allLessThan(@PathVariable double price) {
		return ResponseEntity.ok(productService.priceLessThan(price));
	}
	
	@GetMapping("/greaterthan/{price}")
	public ResponseEntity<List<ProductWithCategoryDTO>> allGreaterThan(@PathVariable double price) {
		return ResponseEntity.ok(productService.priceGreaterThan(price));
	}
}
