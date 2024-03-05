package com.generation.cultdrugstore.mapper;

import org.springframework.stereotype.Component;

import com.generation.cultdrugstore.dto.CategoryCreateDTO;
import com.generation.cultdrugstore.dto.CategoryDTO;
import com.generation.cultdrugstore.dto.ProductDTO;
import com.generation.cultdrugstore.dto.ProductWithCategoryDTO;
import com.generation.cultdrugstore.model.Category;
import com.generation.cultdrugstore.model.Product;

@Component
public class ProductMapper {
	
	public ProductDTO toDTO(Product product) {
		var productDTO = new ProductDTO(
				product.getId(),
                product.getProductName(),
                product.getPrice(),
                product.getPhoto());
		return productDTO;
	}
	
	public ProductWithCategoryDTO toDTOWithCategory(Product product) {
		CategoryDTO categoryDTO = new CategoryDTO(
	            product.getCategory().getId(),
	            product.getCategory().getDescription()
	    );
		
		var productWithCategoryDTO = new ProductWithCategoryDTO(
				product.getId(),
                product.getProductName(),
                product.getPrice(),
                product.getPhoto(),
                categoryDTO);
		return productWithCategoryDTO;
	}
	
	public Product toEntity(ProductWithCategoryDTO productWithCategoryDTO) {
        Product product = new Product();
        product.setId(productWithCategoryDTO.id());
        product.setProductName(productWithCategoryDTO.productName());
        product.setPrice(productWithCategoryDTO.price());
        product.setPhoto(productWithCategoryDTO.photo());
        
        CategoryDTO categoryDTO = productWithCategoryDTO.category();
        Category category = new Category();
        category.setId(categoryDTO.id());
        category.setDescription(categoryDTO.description());
        product.setCategory(category);
        
        return product;
    }
}
