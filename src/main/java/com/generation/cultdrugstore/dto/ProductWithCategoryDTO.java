package com.generation.cultdrugstore.dto;

import java.math.BigDecimal;

public record ProductWithCategoryDTO(Long id, String productName, BigDecimal price, String photo, CategoryDTO category) {}
