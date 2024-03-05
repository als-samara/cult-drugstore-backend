package com.generation.cultdrugstore.dto;

import java.math.BigDecimal;

public record ProductDTO(Long id, String productName, BigDecimal price, String photo) {}
