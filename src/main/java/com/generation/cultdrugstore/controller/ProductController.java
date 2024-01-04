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

import com.generation.cultdrugstore.model.*;
import com.generation.cultdrugstore.repository.CategoryRepository;
import com.generation.cultdrugstore.repository.ProductRepository;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired 
	CategoryRepository categoryRepository;
	
	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAll(){
		return ResponseEntity.ok(productRepository.findAll());
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Product> getById(@PathVariable Long id){
		return productRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID not found!"));
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Product>> getByName(@PathVariable String name) {
		return ResponseEntity.ok(productRepository.findAllByProductNameContainingIgnoreCase(name));
	}
	
	@PostMapping("/create")
	public ResponseEntity<Product> post(@Valid @RequestBody Product product) {
		return categoryRepository.findById(product.getCategory().getId())
				.map(existingCategory -> {
					return ResponseEntity.status(HttpStatus.CREATED)
							.body(productRepository.save(product));
				})
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category does not exist!"));
	}
	
	@PutMapping("/update")
	public ResponseEntity<Product> put(@Valid @RequestBody Product product) {
	    return productRepository.findById(product.getId())
	            .map(existingProduct -> {
	                if (categoryRepository.existsById(product.getCategory().getId()))
	                    return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(product));
	    
	                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category does not exist!");
	                
	            })
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist! Check the typed ID."));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		productRepository.findById(id)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found"));

	    productRepository.deleteById(id);
	}
	
	@GetMapping("/lessthan/{price}")
	public List<Product> listarProdutosMenoresQue(@PathVariable double price) {
		return productRepository.findByPriceLessThan(price);
	}
	
	@GetMapping("/greaterthan/{price}")
	public List<Product> listarProdutosMaioresQue(@PathVariable double price) {
		return productRepository.findByPriceGreaterThan(price);
	}
}
