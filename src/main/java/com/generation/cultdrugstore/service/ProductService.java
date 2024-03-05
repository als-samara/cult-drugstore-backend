package com.generation.cultdrugstore.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.generation.cultdrugstore.dto.ProductWithCategoryDTO;
import com.generation.cultdrugstore.exception.category.CategoryNotFoundException;
import com.generation.cultdrugstore.exception.product.ProductNotFoundException;
import com.generation.cultdrugstore.mapper.ProductMapper;
import com.generation.cultdrugstore.model.Category;
import com.generation.cultdrugstore.model.Product;
import com.generation.cultdrugstore.repository.CategoryRepository;
import com.generation.cultdrugstore.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired ProductRepository productRepository;
	@Autowired ProductMapper productMapper;
	@Autowired CategoryRepository categoryRepository;
	
	public List<ProductWithCategoryDTO> getAll(){
		List<Product> products = productRepository.findAll();
		return products.stream()
				.map(productMapper::toDTOWithCategory)
				.collect(Collectors.toList());
	}
	
	public ProductWithCategoryDTO getById(Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		Product product = optionalProduct.orElseThrow(() -> new ProductNotFoundException("O Produto não foi localizado"));
		return productMapper.toDTOWithCategory(product);
	}
	
	public List<ProductWithCategoryDTO> getByName(String name) {
		List<Product> products = productRepository.findAllByProductNameContainingIgnoreCase(name);
		
		if(products.isEmpty())
			throw new ProductNotFoundException("Nenhum produto encontrado!");
		
		return products.stream()
				.map(productMapper::toDTOWithCategory)
				.collect(Collectors.toList());
	}
	
	public ProductWithCategoryDTO create(ProductWithCategoryDTO productWithCategoryDTO) {
		Product product = productMapper.toEntity(productWithCategoryDTO);
		
		Category existingCategory = categoryRepository.findById(productWithCategoryDTO.category().id())
	            .orElseThrow(() -> new CategoryNotFoundException("A Categoria informada não existe"));
		
	    Product savedProduct = productRepository.save(product);
	    return productMapper.toDTOWithCategory(savedProduct);
        
    }
	
	public ProductWithCategoryDTO update(ProductWithCategoryDTO productWithCategoryDTO) {
	    Product productEntity = productMapper.toEntity(productWithCategoryDTO);

	    // Check if the product exists
	    productRepository.findById(productEntity.getId())
	        .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));

	    // Check if the category exists
	    if (!categoryRepository.existsById(productEntity.getCategory().getId())) {
	        throw new CategoryNotFoundException("Categoria não encontrada");
	    }

	    // Save the updated product entity
	    Product savedProduct = productRepository.save(productEntity);

	    // Map the saved product entity back to DTO
	    return productMapper.toDTOWithCategory(savedProduct);
	}

	 public void delete(Long id) {
		 Optional<Product> optionalProduct = productRepository.findById(id);
	        if(optionalProduct.isEmpty())
	        	throw new CategoryNotFoundException("Produto não encontrado");
	        try {
	        	productRepository.deleteById(id);
	        } catch (Exception e) {
	            throw new RuntimeException("Não foi possível realizar a exclusão!");
	        } 
	 }
	 
	 public List<ProductWithCategoryDTO> priceLessThan(@PathVariable double price){
		 List<Product> products = productRepository.findByPriceLessThan(price);
		 return products.stream()
		 		.map(productMapper::toDTOWithCategory)
		 		.collect(Collectors.toList());
	 }
	 
	 public List<ProductWithCategoryDTO> priceGreaterThan(@PathVariable double price){
		 List<Product> products = productRepository.findByPriceGreaterThan(price);
		 return products.stream()
		 		.map(productMapper::toDTOWithCategory)
		 		.collect(Collectors.toList());
	 }
	
}
