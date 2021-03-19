package com.example.Apartment.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Apartment.DTO.ApartmentDTO;
import com.example.Apartment.DTO.OwnerDetailsDTO;
import com.example.Apartment.Entity.Apartment;
import com.example.Apartment.Entity.OwnerDetails;
import com.example.Apartment.Entity.UserLogin;

@Service
public interface ApartmentService {
	
//	public void savedetails(ApartmentDTO apartmentDTO);
	
//	public List<ApartmentDTO> getdetails();

//	List<Apartment> getApdetails();
	
//	void delete(int id);
	
	public List<String> getownerDetails();
	
	public List<OwnerDetails> fetchownerDetails();

	Integer saveUser(UserLogin user);
	
	Optional<UserLogin> findByUsername(String userName);
}
