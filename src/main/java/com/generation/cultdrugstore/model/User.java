package com.generation.cultdrugstore.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name="tb_users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="Username cannot be blank!")
	private String username;
	
	@NotBlank(message = "Name cannot be blank")
	private String name;
	
	@NotBlank(message = "Name cannot be blank")
	@Size(min=8, message="Password cannot be under 8 characters")
	private String password;
	
	@Column(length = 5000)
	@Size(max = 5000, message = "O link da foto não pode ser maior do que 5000 caracteres")
	private String photo;
	
	// fetch type lazy = products won't be loaded by default with the user, unless it's explicitly accessed
	// user is the name of the field in the Product entity that maps the relationship
	// cascadeType removes all products if the user is deleted
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("user")
	private List<Product> products;
	
	// rolePharmacist , roleAdmin , roleUser
	@NotEmpty(message = "Indicate at least one role")
	private List<String> roles;
}
