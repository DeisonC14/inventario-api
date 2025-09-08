package com.almacen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import
org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.almacen.dto.AuthRequest;
import com.almacen.dto.AuthResponse;
import com.almacen.entity.User;
import com.almacen.repository.UserRepository;
import com.almacen.security.JwtUtil;
import com.almacen.service.CustomUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private CustomUserService customUserService;
	@Autowired
	private UserRepository userRepository;
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(),
							request.getPassword())
					);
			final UserDetails userDetails =
					customUserService.loadUserByUsername(request.getUsername());
			final String token = jwtUtil.generateToken(userDetails);
			return ResponseEntity.ok(new AuthResponse(token));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
		}
	}
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody AuthRequest request) {
		if (userRepository.findByUsername(request.getUsername()).isPresent()) {
			return ResponseEntity.badRequest().body("El usuario ya existe");
		}
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(new
				BCryptPasswordEncoder().encode(request.getPassword()));
		userRepository.save(user);
		return ResponseEntity.ok("Usuario registrado correctamente");
	}
}