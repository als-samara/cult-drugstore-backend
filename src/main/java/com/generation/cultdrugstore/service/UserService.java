package com.generation.cultdrugstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import com.generation.cultdrugstore.dto.TokenDTO;
import com.generation.cultdrugstore.dto.UserDTO;
import com.generation.cultdrugstore.dto.UserUpdateDTO;
import com.generation.cultdrugstore.exception.user.UserAlreadyExistsException;
import com.generation.cultdrugstore.exception.user.UserNotFoundException;
import com.generation.cultdrugstore.mapper.UserMapper;
import com.generation.cultdrugstore.model.User;
import com.generation.cultdrugstore.model.UserLogin;
import com.generation.cultdrugstore.repository.UserRepository;
import com.generation.cultdrugstore.security.JwtService;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	UserMapper userMapper;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	public UserDTO getUserById(@PathVariable Long id) {
		var userEntity = userRepository.findById(id);
		if (userEntity.isEmpty())
			throw new UserNotFoundException("Id não encontrado");
		return userMapper.toDTO(userEntity.get());
	}
	
	public UserDTO getUserByUsername(@PathVariable String username) {
		var userEntity = userRepository.findByUsername(username);
		if (userEntity.isEmpty())
			throw new UserNotFoundException("Email não encontrado");
		return userMapper.toDTO(userEntity.get());
	}

	public UserDTO registerUser(User user) {

		// check if a user with the same username already exists in the database
		// and throws a custom exception if it does
		Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
		if (existingUser.isPresent()) {
			throw new UserAlreadyExistsException("O usuário já existe");
		}

		// encrypts the password of the user object
		user.setPassword(encryptPassword(user.getPassword()));
		
		// persists the object on DB
		var createdUser = userRepository.save(user);

		// maps the createdUser object to a UserDTO object.
		// It returns the UserDTO object, which represents the newly created user
		return userMapper.toDTO(createdUser);
	}

	public UserUpdateDTO updateUser(User user) {

		// searchs user by id
		if (userRepository.findById(user.getId()).isPresent()) {

			// check if a user with the same username already exists in the database
			// and throws a custom exception if it does
			Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
			if ((existingUser.isPresent()) && (existingUser.get().getId() != user.getId()))
				throw new UserAlreadyExistsException("O usuário já existe");

			// encrypts the password of the user object
			user.setPassword(encryptPassword(user.getPassword()));
			// persists the updated object on DB
			var updatedUser = userRepository.save(user);

			// maps the updatedUser object to a UserUpdateDTO object.
			// It returns the UserUpdateDTO object, which represents the updated user
			return userMapper.toUserUpdateDTO(updatedUser);

		}
		throw new UserNotFoundException("Id não existe!");

	}

	public TokenDTO authenticateUser(Optional<UserLogin> userLogin) {

		// search for user data
	    Optional<User> user = userRepository.findByUsername(userLogin.get().getUsername());

	    // If the user is not found
	    if (user.isEmpty()) {
	        // User not found in the database
	        throw new UserNotFoundException("O e-mail informado não está cadastrado");
	    }

	    // create authentication object with username and password provided for login
	    var credentials = new UsernamePasswordAuthenticationToken(userLogin.get().getUsername(),
	            userLogin.get().getPassword());

	    try {
	        // Authenticate the object
	        Authentication authentication = authenticationManager.authenticate(credentials);

	        // If authentication is completed
	        if (authentication.isAuthenticated()) {

	            // Fill the rest of the attributes of the UserLogin
	            userLogin.get().setId(user.get().getId());
	            userLogin.get().setName(user.get().getName());
	            userLogin.get().setPhoto(user.get().getPhoto());
	            userLogin.get().setToken(generateToken(userLogin.get().getUsername(), userLogin.get().getRoles()));
	            userLogin.get().setPassword("");
	            userLogin.get().setRoles(user.get().getRoles());

	            // returns the filled object and maps to a TokenDTO object
	            return userMapper.toTokenDTO(userLogin.get());

	        }
	    } catch (AuthenticationException e) {
	        // If authentication failed
	        throw new UserNotFoundException("Autenticação falhou, verifique os dados e tente novamente");
	    }

	    // If authentication fails without throwing an exception
	    throw new UserNotFoundException("Autenticação falhou");

	}

	private String encryptPassword(String password) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(password);

	}
	
	// generate token with the username and roles of the user
	private String generateToken(String username, List<String> roles) {
		Optional<User> user = userRepository.findByUsername(username);

		if (user.isPresent()) {
			return "Bearer " + jwtService.generateToken(username, roles);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado", null);
		}
	}

}
