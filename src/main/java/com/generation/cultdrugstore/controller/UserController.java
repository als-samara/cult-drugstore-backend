package com.generation.cultdrugstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.cultdrugstore.dto.TokenDTO;
import com.generation.cultdrugstore.dto.UserDTO;
import com.generation.cultdrugstore.dto.UserUpdateDTO;
import com.generation.cultdrugstore.model.User;
import com.generation.cultdrugstore.model.UserLogin;
import com.generation.cultdrugstore.repository.UserRepository;
import com.generation.cultdrugstore.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAll() {

		return ResponseEntity.ok(userRepository.findAll());

	}

	@GetMapping("/id/{id}")
	public ResponseEntity<UserDTO> getById(@PathVariable Long id) {

		UserDTO userDTO = userService.getUserById(id);
		return ResponseEntity.status(HttpStatus.OK).body(userDTO);
		
	}

	@GetMapping("/user/{user}")
	public ResponseEntity<UserDTO> getByUsername(@PathVariable String user) {

		UserDTO userDTO = userService.getUserByUsername(user);
		return ResponseEntity.status(HttpStatus.OK).body(userDTO);
	}

	@PostMapping("/login")
	public ResponseEntity<TokenDTO> login(@RequestBody Optional<UserLogin> userLogin) {

		TokenDTO tokenDTO = userService.authenticateUser(userLogin);
		return ResponseEntity.status(HttpStatus.OK).body(tokenDTO);

	}

	@PostMapping("/register")
	public ResponseEntity<UserDTO> post(@RequestBody @Valid User user) {

		UserDTO userDTO = userService.registerUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);

	}

	@PutMapping("/update")
	public ResponseEntity<UserUpdateDTO> put(@Valid @RequestBody User user) {

		UserUpdateDTO userUpdateDTO = userService.updateUser(user);
		return ResponseEntity.status(HttpStatus.OK).body(userUpdateDTO);

	}
}
