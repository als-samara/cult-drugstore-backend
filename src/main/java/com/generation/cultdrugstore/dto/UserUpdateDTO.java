package com.generation.cultdrugstore.dto;

import java.util.List;

public record UserUpdateDTO(String name, String password, String photo, List<String> roles) { }
