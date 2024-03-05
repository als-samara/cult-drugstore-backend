package com.generation.cultdrugstore.dto;

import java.util.List;

public record CategoryWithProductsDTO(Long id, String description, List<ProductDTO> products) {}
