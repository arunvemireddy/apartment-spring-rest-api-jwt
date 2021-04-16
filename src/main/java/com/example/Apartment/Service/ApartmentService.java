package com.example.Apartment.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Apartment.DTO.ApartmentDTO;
import com.example.Apartment.DTO.ApartmentDetailsDTO;
import com.example.Apartment.DTO.OwnerDetailsDTO;
import com.example.Apartment.Entity.Apartment;
import com.example.Apartment.Entity.OwnerDetails;
import com.example.Apartment.Entity.UserLogin;

	/**
	 * @author ARUN VEMIREDDY
	 *
	 */
@Service
public interface ApartmentService {

	Integer saveUser(UserLogin user);
	
	Optional<UserLogin> findByUsername(String userName);
}
