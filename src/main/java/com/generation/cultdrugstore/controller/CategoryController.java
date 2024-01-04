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

import com.generation.cultdrugstore.model.Category;
import com.generation.cultdrugstore.repository.CategoryRepository;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/categories")
public class CategoryController {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@GetMapping("/all")
	public ResponseEntity<List<Category>> getAll(){
		return ResponseEntity.ok(categoryRepository.findAll());
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Category> getById(@PathVariable Long id) {
		return categoryRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@GetMapping("description/{description}")
	public ResponseEntity<List<Category>> getByDescription(@PathVariable String description){
		return ResponseEntity.ok(categoryRepository.findAllByDescriptionContainingIgnoreCase(description));
	}
	
	@PostMapping("/create")
	public ResponseEntity<Category> create(@Valid @RequestBody Category category){
		if(categoryRepository.existsByDescriptionIgnoreCase(category.getDescription()))
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists!");
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(categoryRepository.save(category));
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<Category> update(@Valid @RequestBody Category category){
		// Check if a category with the same description (ignoring case) exists and if 
		// a category with the provided ID doesn't exist
		if(categoryRepository.existsByDescriptionIgnoreCase(category.getDescription()) && 
				categoryRepository.findById(category.getId()).isEmpty())
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists!");
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(categoryRepository.save(category));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		categoryRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!"));
		
		categoryRepository.deleteById(id);
	}
	
}