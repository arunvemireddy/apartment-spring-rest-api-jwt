package com.example.Apartment.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Apartment.Entity.ApartmentDetails;

@Service
public interface ApartmentDetailsService {

	public List<ApartmentDetails> getApartmentDetails();
}
