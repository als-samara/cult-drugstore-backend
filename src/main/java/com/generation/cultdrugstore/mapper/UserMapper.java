package com.generation.cultdrugstore.mapper;

import org.springframework.stereotype.Component;

import com.generation.cultdrugstore.dto.TokenDTO;
import com.generation.cultdrugstore.dto.UserDTO;
import com.generation.cultdrugstore.dto.UserUpdateDTO;
import com.generation.cultdrugstore.model.User;
import com.generation.cultdrugstore.model.UserLogin;

@Component
public class UserMapper {
	
	public UserDTO toDTO(User user) {
		var userDTO = new UserDTO(
				user.getName(), 
				user.getUsername());
				
		return userDTO;
	}
	
	public UserUpdateDTO toUserUpdateDTO(User user) {
		var userUpdateDTO = new UserUpdateDTO(
				user.getName(), 
				user.getUsername(),
				user.getPhoto(),
				user.getRoles());
				
		return userUpdateDTO;
	}
	
	public TokenDTO toTokenDTO(UserLogin userLogin) {
		var tokenDTO = new TokenDTO(
				userLogin.getToken());
				return tokenDTO;
	}
}
