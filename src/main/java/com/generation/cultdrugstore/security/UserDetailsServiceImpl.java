package com.generation.cultdrugstore.security;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.generation.cultdrugstore.model.User;
import com.generation.cultdrugstore.repository.UserRepository;

@Service // this class is responsible for authenticate the user
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        return userOptional.map(user -> {
            List<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    authorities
            );
        }).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

	/*
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findByUsername(username);
		
		if(user.isPresent())
			return new UserDetailsImpl(user.get()); // 'opens' the optional and create a new UserDetailsImpl
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN); // Authentication fails if the user doesn't exist
	}
	// the method returns a UserDetails with the required details for AUTHENTICATION (username, password and authorities)
	*/

}
