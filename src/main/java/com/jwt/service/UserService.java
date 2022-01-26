package com.jwt.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.modal.UserLogin;
import com.jwt.repo.UserLoginRepo;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserLoginRepo userLoginRepo;
	
	@Autowired
	private PasswordEncoder bCryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		//get user from database using the username ;
		
		UserLogin login = userLoginRepo.findById(username).get();
		//System.out.println("userName: "+login.getUsername() +"password :"+ login.getPassword());
		
		return new User(username, login.getPassword(), new ArrayList<>());

	
		
		//return new User("admin","password",new ArrayList<>());
	}

	public UserLogin saveUser(UserLogin userLogin) {
		// TODO Auto-generated method stub
		userLogin.setPassword(bCryptEncoder.encode(userLogin.getPassword()));
		return userLoginRepo.save(userLogin);
	}

	public UserLogin login(UserLogin userLogin) throws Exception {
		UserLogin userLogin2 = userLoginRepo.findById(userLogin.getUsername()).get();
		
//		System.out.println("==============================================================================");
//		System.out.println("userLogin: "+userLogin.getUsername());
//		System.out.println("userLogin_pwssword: "+userLogin.getPassword());
//		
//		System.out.println("==============================================================================");
//		System.out.println("userName: "+userLogin2.getUsername());
//		System.out.println("password: "+userLogin2.getPassword());
		boolean matches = bCryptEncoder.matches(userLogin.getPassword(), userLogin2.getPassword());
		if(matches) {
			return userLogin2;
		}
		throw new Exception("Credential has not matched ...");
		

	}
	
	

}
