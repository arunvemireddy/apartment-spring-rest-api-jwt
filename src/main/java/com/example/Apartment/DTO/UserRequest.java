package com.example.Apartment.DTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserRequest {
	
	@NotEmpty(message = "username cannot be empty")
	@NotNull(message = "username cannot be null")
	private String userName;
	@NotEmpty(message = "password cannot be empty")
	@NotNull(message = "password cannot be null")
	private String password;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
}
