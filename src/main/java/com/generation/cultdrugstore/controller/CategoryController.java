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
import org.springframework.web.bind.annotation.RestController;

import com.generation.cultdrugstore.dto.CategoryCreateDTO;
import com.generation.cultdrugstore.dto.CategoryWithProductsDTO;
import com.generation.cultdrugstore.model.Category;
import com.generation.cultdrugstore.model.Product;
import com.generation.cultdrugstore.repository.CategoryRepository;
import com.generation.cultdrugstore.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/categories")
public class CategoryController {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired CategoryService categoryService;
	
	@GetMapping("/all")
	public ResponseEntity<List<CategoryWithProductsDTO>> getAll(){
		return ResponseEntity.ok(categoryService.getAll());
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<CategoryWithProductsDTO> getById(@PathVariable Long id) {
		return ResponseEntity.ok(categoryService.findById(id));
	}
	
	@GetMapping("description/{description}")
	public ResponseEntity<List<CategoryWithProductsDTO>> getByDescription(@PathVariable String description){
		return ResponseEntity.ok(categoryService.getByDescription(description));
	}
	
	@PostMapping("/create")
	public ResponseEntity<CategoryWithProductsDTO> create(@Valid @RequestBody CategoryCreateDTO categoryCreateDTO){
		CategoryWithProductsDTO createdCategoryDTO = categoryService.create(categoryCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategoryDTO);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<CategoryWithProductsDTO> update(@Valid @RequestBody Category category){
		CategoryWithProductsDTO updatedCategoryDTO = categoryService.update(category);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCategoryDTO);
				
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		categoryService.delete(id);
        return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getByCategoria(@PathVariable String category) {
        List<Product> produtos = categoryService.getByCategory(category);
        return ResponseEntity.ok(produtos);
    }
	
}
