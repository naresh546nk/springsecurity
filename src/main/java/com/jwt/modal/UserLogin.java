package com.jwt.modal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserLogin {
	@Id
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserLogin( String userName, String password) {
		super();
		this.username = userName;
		this.password = password;
	}
	public UserLogin() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}