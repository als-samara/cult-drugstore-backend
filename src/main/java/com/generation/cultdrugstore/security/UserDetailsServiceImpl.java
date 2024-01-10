package com.generation.cultdrugstore.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.cultdrugstore.model.User;
import com.generation.cultdrugstore.repository.UserRepository;

@Service // this class is responsible for authenticate the user
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findByUsername(username);
		
		if(user.isPresent())
			return new UserDetailsImpl(user.get()); // 'opens' the optional and create a new UserDetailsImpl
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN); // Authentication fails if the user doesn't exist
	}
	// the method returns a UserDetails with the required details for AUTHENTICATION (username, password and authorities)
}
