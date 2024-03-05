package com.generation.cultdrugstore.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateDTO(@NotBlank(message = "A descrição não pode estar vazia") String description) {}
