package com.generation.cultdrugstore.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;

		try {
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				token = authHeader.substring(7);
				username = jwtService.extractUsername(token);
			}

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				if (jwtService.validateToken(token, userDetails)) {
					
					// Obtenha as roles do UserDetails e crie uma lista de GrantedAuthority
			        List<GrantedAuthority> authorities = userDetails.getAuthorities().stream()
			                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
			                .collect(Collectors.toList());
			        
			     // Configurar o UsernamePasswordAuthenticationToken com as roles
			        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
			                userDetails, null, authorities);
			        
			     // Configurar detalhes do token
			        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}

			}
			filterChain.doFilter(request, response);

		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| ResponseStatusException e) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			return;
		}
	}
}
