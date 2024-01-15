package com.example.Apartment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Apartment.Service.ApartmentDetailsService;

/**
 * @author ARUN VEMIREDDY
 *
 */
@RestController
@RequestMapping(path = "/api")
public class ApartmentDetailsController {

	private ApartmentDetailsService apartmentDetailsService;

	@Autowired
	public ApartmentDetailsController(ApartmentDetailsService apartmentDetailsService) {
		this.apartmentDetailsService = apartmentDetailsService;
	}

	// fetch Apartment Details
	@GetMapping(path = "/getApartmentDetails")
	public ResponseEntity<?> getApartmentDetails() {
		ResponseEntity<?> response = new ResponseEntity<>(apartmentDetailsService.getApartmentDetails(), HttpStatus.OK);
		return response;
	}

}
