package com.generation.cultdrugstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.cultdrugstore.model.User;
import com.generation.cultdrugstore.model.UserLogin;
import com.generation.cultdrugstore.repository.UserRepository;
import com.generation.cultdrugstore.security.JwtService;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	public Optional<User> registerUser(User user) {

		if (userRepository.findByUsername(user.getUsername()).isPresent())
			return Optional.empty();

		user.setPassword(encryptPassword(user.getPassword()));
		return Optional.of(userRepository.save(user));
		
	}

	
	public Optional<User> updateUser(User user) {

		if (userRepository.findById(user.getId()).isPresent()) {

			Optional<User> buscaUsuario = userRepository.findByUsername(user.getUsername());

			if ((buscaUsuario.isPresent()) && (buscaUsuario.get().getId() != user.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

			user.setPassword(encryptPassword(user.getPassword()));
			return Optional.ofNullable(userRepository.save(user));

		}	
		// returns an empty optional if the id doesn't exist
		return Optional.empty();

	}
	

	public Optional<UserLogin> authenticateUser(Optional<UserLogin> userLogin) {

		// create authentication object with username and password provided for login
		var credentials = new UsernamePasswordAuthenticationToken(userLogin.get().getUsername(),
				userLogin.get().getPassword());
		// Authenticate the object
		Authentication authentication = authenticationManager.authenticate(credentials);

		// If authentication is completed
		if (authentication.isAuthenticated()) {

			// searchs for user data
			Optional<User> user = userRepository.findByUsername(userLogin.get().getUsername());

			// If the user is found
			if (user.isPresent()) {

				// Fill the rest of the atributes of the UserLogin
				userLogin.get().setId(user.get().getId());
				userLogin.get().setName(user.get().getName());
				userLogin.get().setPhoto(user.get().getPhoto());
				userLogin.get().setToken(generateToken(userLogin.get().getUsername(),userLogin.get().getRoles()));
				userLogin.get().setPassword("");
				userLogin.get().setRoles(user.get().getRoles());

				// returns the filled object
				return userLogin;

			}

		}
		// if authentication failed, returns an empty optional
		return Optional.empty();

	}

	private String encryptPassword(String password) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(password);

	}

	private String generateToken(String username, List<String> roles) {
		Optional<User> user = userRepository.findByUsername(username);

	    if (user.isPresent()) {
	        return "Bearer " + jwtService.generateToken(username, roles);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado", null);
	    }
	}

}
