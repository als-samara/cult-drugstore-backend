package com.generation.cultdrugstore.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name="tb_products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Product name cannot be blank")
	private String productName;
	
	// establishes a minimum value for the attribute, including the 'value'
	@DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be less than 0")
	@Digits(integer = 5, fraction = 2)
	private BigDecimal price;
	
	@Column(length=5000)
	@Size(message = "O atríbuto foto pode ter no máximo 5000 caracteres.")
	private String photo;
	
	@ManyToOne
	@JsonIgnoreProperties("products")
	private Category category;
	
	@ManyToOne
	@JsonIgnoreProperties("products")
	private User user;
}
