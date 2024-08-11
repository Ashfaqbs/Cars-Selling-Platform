package com.ashfaq.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashfaq.application.config.CustomAppUserDetailService;
import com.ashfaq.application.config.JWTService;
import com.ashfaq.application.dto.LoginForm;
import com.ashfaq.application.model.ApplicationUser;
import com.ashfaq.application.repository.ApplicationUserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private ApplicationUserRepository myUserRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomAppUserDetailService userDetailService;

	// this api should be accessable to all
	@PostMapping("/user")
	public ResponseEntity<String> createUser(@RequestBody ApplicationUser user) {
		log.info("Request register user : {}", user);

		// // if the role is "ADMIN" and reject the request if it is
		// if ("ADMIN".equalsIgnoreCase(user.getRole())) {
		// log.warn("Attempt to register with ADMIN role: {}", user);
		// return ResponseEntity.badRequest().body("Registration with ADMIN role is not
		// allowed");
		// }

		// Split roles by comma and check if "ADMIN" is present
		String[] roles = user.getRole().split(",");
		for (String role : roles) {
			if ("ADMIN".equalsIgnoreCase(role.trim())) {
				log.warn("Attempt to register with ADMIN role: {}", user);
				return ResponseEntity.badRequest().body("Registration with ADMIN role is not allowed");
			}
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		ApplicationUser appUser = myUserRepository.save(user);

		log.info("post Request register user : {}", appUser);

		return ResponseEntity.ok("User registered successfully with username: " + appUser.getUsername());
	}

	/*
	 * 
	 * Scenarios:
	 * 
	 * http://localhost:8080/register/user
	 * 
	 * 
	 * { "username": "test-case", "password": "adminPass789", "email":
	 * "ashfaq.admin@example.com", "role": "ADMIN,USER,SELLER" }
	 * 
	 * Registration with ADMIN role is not allowed
	 * 
	 * 
	 * 
	 * { "username": "test-case", "password": "adminPass789", "email":
	 * "ashfaq.admin@example.com", "role": "USER,SELLER" }
	 * 
	 * User registered successfully with username: test-case
	 * 
	 * 
	 */

	@PostMapping("/authenticate")
	public ResponseEntity<String> authAndGetTokenold(@RequestBody LoginForm loginFormuser) {

		Authentication authenticationResult = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginFormuser.username(), loginFormuser.password()));

		// log.info(authenticationResult.toString() + "authenticationResult
		// ************");

		if (authenticationResult.isAuthenticated()) {
			// we need to return JWT token

			UserDetails userDetails = userDetailService.loadUserByUsername(loginFormuser.username());
			// log.info(loginFormuser.username() + "User found in database ************");

			String jwtToken = jwtService.generateToken(userDetails);// this functions takes Userdetails param and we
			// have loginform with username and pwd ,
			// so we will use CusotmuserDetailService funciont which takes username
			// validates and returns Userdetails

			return ResponseEntity.ok().body("Token: " + jwtToken);

		} else {

			throw new RuntimeException("Invalid username or password" + loginFormuser.username());// this wont work as
																									// if auth is failed
			// directly we get 401 error

		}

	}
}
