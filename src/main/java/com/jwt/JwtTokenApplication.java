package com.jwt;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication

public class JwtTokenApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtTokenApplication.class, args);
	}
	
	
	@Bean
	PasswordEncoder NoOppasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	@Primary
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
