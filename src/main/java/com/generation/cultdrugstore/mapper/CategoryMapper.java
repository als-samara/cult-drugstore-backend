package com.generation.cultdrugstore.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.generation.cultdrugstore.dto.CategoryCreateDTO;
import com.generation.cultdrugstore.dto.CategoryWithProductsDTO;
import com.generation.cultdrugstore.dto.ProductDTO;
import com.generation.cultdrugstore.model.Category;

@Component
public class CategoryMapper {
	
	@Autowired
    private ProductMapper productMapper;
	
	public CategoryWithProductsDTO toDTO(Category category) {
		
		 List<ProductDTO> productsDTO = new ArrayList<>();
		    if (category.getProducts() != null) {
		        productsDTO = category.getProducts().stream()
		                .map(productMapper::toDTO)
		                .collect(Collectors.toList());
		    }
		    var categoryDTO = new CategoryWithProductsDTO(
		            category.getId(),
		            category.getDescription(),
		            productsDTO
		    );
		    return categoryDTO;
    }
	
	public Category toEntity(CategoryCreateDTO categoryCreateDTO) {
        Category category = new Category();
        category.setDescription(categoryCreateDTO.description());
        return category;
    }

}
