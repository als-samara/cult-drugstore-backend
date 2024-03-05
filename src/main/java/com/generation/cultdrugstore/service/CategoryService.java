package com.generation.cultdrugstore.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.cultdrugstore.dto.CategoryCreateDTO;
import com.generation.cultdrugstore.dto.CategoryWithProductsDTO;
import com.generation.cultdrugstore.exception.category.CategoryAlreadyExistsException;
import com.generation.cultdrugstore.exception.category.CategoryNotFoundException;
import com.generation.cultdrugstore.mapper.CategoryMapper;
import com.generation.cultdrugstore.model.Category;
import com.generation.cultdrugstore.model.Product;
import com.generation.cultdrugstore.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired CategoryRepository categoryRepository;
	@Autowired CategoryMapper categoryMapper;
	
	public List<CategoryWithProductsDTO> getAll(){
		List<Category> categories = categoryRepository.findAll();
				return categories.stream()
		                .map(categoryMapper::toDTO)
		                .collect(Collectors.toList());
	}
	
	public CategoryWithProductsDTO findById(Long id) {
		Optional<Category> optionalCategory = categoryRepository.findById(id);
	    Category category = optionalCategory.orElseThrow(() -> new CategoryNotFoundException(
	            "Categoria não encontrada! Id: " + id));
	    return categoryMapper.toDTO(category);
    }
	
	public List<CategoryWithProductsDTO> getByDescription(String description){
		List<Category> categorieEntities = categoryRepository.findAllByDescriptionContainingIgnoreCase(description);
		
		if (categorieEntities.isEmpty()) {
	        throw new CategoryNotFoundException("Nenhuma categoria encontrada contendo a descrição pesquisada: " + description);
	    }
		
		return categorieEntities.stream()
					.map(categoryMapper::toDTO)
					.collect(Collectors.toList());		
	}
	
	public CategoryWithProductsDTO create(CategoryCreateDTO categoryCreateDTO) {
		Category category = categoryMapper.toEntity(categoryCreateDTO);
		
		if(categoryRepository.existsByDescriptionIgnoreCase(category.getDescription()))
				throw new CategoryAlreadyExistsException("Categoria já existe");
		
	    Category savedCategory = categoryRepository.save(category);
	    return categoryMapper.toDTO(savedCategory);
        
    }
	
	public CategoryWithProductsDTO update(Category category) {
		// Checks if a category with the same description (ignoring case) exists and if 
		// a category with the provided ID doesn't exist
		if(categoryRepository.existsByDescriptionIgnoreCase(category.getDescription()) && 
				categoryRepository.findById(category.getId()).isEmpty())
			throw new CategoryAlreadyExistsException("Categoria já existe");
		
		Category updatedCategory = categoryRepository.save(category);
		return categoryMapper.toDTO(updatedCategory);
	}
	
	public void delete(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty())
        	throw new CategoryNotFoundException("Categoria não encontrada");
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível realizar a exclusão!");
        }
    }

    public List<Product> getByCategory(String categoryDescription) {
        return categoryRepository.findProductsByCategoryDescription(categoryDescription);
    }
	
}
