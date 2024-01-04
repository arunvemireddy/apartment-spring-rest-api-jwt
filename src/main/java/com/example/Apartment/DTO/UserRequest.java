package com.example.Apartment.DTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserRequest {

	@NotEmpty(message = "username cannot be empty")
	@NotNull(message = "username cannot be null")
	private String username;
	@NotEmpty(message = "password cannot be empty")
	@NotNull(message = "password cannot be null")
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

}
