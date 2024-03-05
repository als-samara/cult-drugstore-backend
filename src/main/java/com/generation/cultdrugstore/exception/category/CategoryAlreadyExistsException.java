package com.generation.cultdrugstore.exception.category;

public class CategoryAlreadyExistsException extends CategoryException {
	
	private static final long serialVersionUID = 1L;

	public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
