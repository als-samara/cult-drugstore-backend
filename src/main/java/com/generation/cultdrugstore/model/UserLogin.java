package com.generation.cultdrugstore.model;

import lombok.Data;

@Data
public class UserLogin {
	private Long id;
	private String username;
	private String name;
	private String password;
	private String photo;
	private String token;
}
