package com.generation.cultdrugstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.generation.cultdrugstore.model.Product;
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
		// The 'findAll()' method retrieves all products from the repository
		return ResponseEntity.ok(productRepository.findAll());
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Product> getById(@PathVariable Long id){
		return productRepository.findById(id) // Retrieves a product from the repository by its ID
				.map(ResponseEntity::ok) // If the product is found, maps it to a ResponseEntity with status 200 (OK)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID not found!")); // If the product is not found, throws a ResponseStatusException with status 404 (NOT FOUND)
	}
	
	@GetMapping("/name/{name}")
	// retrieves a list of products based on the provided name or part of the name (containing)
	public ResponseEntity<List<Product>> getByName(@PathVariable String name) {
		// returns a ResponseEntity OK even if the list returned it's empty
		return ResponseEntity.ok(productRepository.findAllByProductNameContainingIgnoreCase(name));
	}
	
	@PreAuthorize("hasRole('rolePharmacist', 'roleAdmin')")
	@PostMapping("/create")
	public ResponseEntity<Product> post(@Valid @RequestBody Product product) {
		// Searches for a category based on the ID obtained from the product on RequestBody
		return categoryRepository.findById(product.getCategory().getId())
				// maps the category, if it exists, create the product and return a ResponseEntity with status 201 (Created)
				.map(existingCategory -> {
					return ResponseEntity.status(HttpStatus.CREATED)
							.body(productRepository.save(product)); // the body response returns the category created
				})
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category does not exist!"));
	}
	
	@PreAuthorize("hasRole('rolePharmacist', 'roleAdmin')")
	@PutMapping("/update")
	public ResponseEntity<Product> put(@Valid @RequestBody Product product) {
		// Searches for a product in the productRepository based on the ID obtained from the product object passed in the request
	    return productRepository.findById(product.getId())
	            .map(existingProduct -> {
	            	// If the product exists, check if the its category exists and save the product
	                if (categoryRepository.existsById(product.getCategory().getId()))
	                    return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(product));
	    
	                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category does not exist!");
	                
	            })
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist! Check the typed ID."));
	}
	
	@PreAuthorize("hasRole('rolePharmacist', 'roleAdmin')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		// Find the product by its ID in the productRepository; throw an exception if not found
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
