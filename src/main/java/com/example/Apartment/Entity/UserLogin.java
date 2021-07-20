package com.example.Apartment.Entity;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

	/**
	 * @author ARUN VEMIREDDY
	 *
	 */
@Entity
@Data
public class UserLogin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull(message = "Name cannot be null")
	@NotEmpty(message = "Name cannot be empty")
	@Size(min = 3,message = "Name should be minimum 4 characters")
	private String name;
	@NotNull(message = "username cannot be null")
	@NotEmpty(message = "username cannot be empty")
	@Size(min = 4,message = "username should be minimum 4 characters")
	private String username;
	@NotNull(message = "password cannot be null")
	@NotEmpty(message = "password cannot be empty")
	@Size(min = 8,message = "password should be minimum 8 characters")
	private String password;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="roles",joinColumns = @JoinColumn(name="id"))
	@Column(name="role")
	private Set<String> roles;
	@Email(message = "Email should be valid")
	@NotNull(message = "cannot be not null")
	private String email;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
}
