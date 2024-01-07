package com.example.Apartment.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Apartment.Entity.UserLogin;

/**
 * @author ARUN VEMIREDDY
 *
 */
@Service
public interface RegisterUserService {

	Integer saveUser(UserLogin user);

	Optional<UserLogin> findByUsername(String userName);
}
