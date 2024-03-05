package com.generation.cultdrugstore.mapper;

import org.springframework.stereotype.Component;

import com.generation.cultdrugstore.dto.ProductDTO;
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
}
