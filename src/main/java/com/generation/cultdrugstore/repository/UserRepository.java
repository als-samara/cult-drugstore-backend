package com.generation.cultdrugstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.cultdrugstore.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByUser(String user);
}
