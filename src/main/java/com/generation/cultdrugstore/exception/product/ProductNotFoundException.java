package com.generation.cultdrugstore.exception.product;

public class ProductNotFoundException extends ProductException {
	private static final long serialVersionUID = 1L;
	
	public ProductNotFoundException(String message) {
		super(message);
	}
}
