package com.example.Apartment.Service;

import java.util.Optional;

import com.example.Apartment.Entity.UserLogin;

public interface LoginService {
	
	Optional<UserLogin> findByUsername(String userName);

}
