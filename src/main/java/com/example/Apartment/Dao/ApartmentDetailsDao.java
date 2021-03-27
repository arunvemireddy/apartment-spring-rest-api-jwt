package com.example.Apartment.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Apartment.Entity.ApartmentDetails;
import com.example.Apartment.Entity.UserLogin;

public interface ApartmentDetailsDao extends JpaRepository<ApartmentDetails, Integer> {

	@Query(value = "select * from apartment_details where id=1", nativeQuery = true)
	List<ApartmentDetails> getApartmentDetails();
}
