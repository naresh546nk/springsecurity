package com.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.jwt.modal.JwtRequest;
import com.jwt.modal.JwtResponse;
import com.jwt.modal.UserLogin;
import com.jwt.service.UserService;
import com.jwt.utility.JwtUtilltiy;

@RestController
//@RequestMapping("/user")
public class UserController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtilltiy jwtUtilltiy;
	
	@Autowired
	private PasswordEncoder bCryptEncode;
	
	
	@GetMapping("/")
	public String test() {
		return "User api is working fine with JWT token ...";
	}
	
	
	@PostMapping("/user")
	public UserLogin addUser(@RequestBody UserLogin userLogin) {
		return userService.saveUser(userLogin);
	}
	
	@PostMapping("/login")
	public UserLogin login(@RequestBody UserLogin userLogin) throws Exception {
		return userService.login(userLogin);
	}
	

	@PostMapping("/authenticate")
	public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
		
		try {
		authenticationManager.authenticate(new 
				UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		}catch (BadCredentialsException e){
			//return new JwtResponse("Invalid credential");
			//return  "Invalid credential";
		
			throw new Exception("Invalid credential "+e);
		}catch (Exception e){
			JwtResponse jwtResponseToken = new JwtResponse();
			jwtResponseToken.setMessage(e.getMessage());
			return jwtResponseToken;
		}
		
		 final UserDetails userDetails =userService.loadUserByUsername(jwtRequest.getUsername());
		 
		 final String tokenString=jwtUtilltiy.generateToken(userDetails);
		 JwtResponse jwtResponseToken = new JwtResponse(tokenString);
		 jwtResponseToken.setMessage("token created successfully");
		 
		 //return tokenString;
		 return jwtResponseToken;
	}

}
