package com.generation.cultdrugstore.dto;

import java.util.List;

public record CategoryDTO(Long id, String description, List<ProductDTO> products) {}
