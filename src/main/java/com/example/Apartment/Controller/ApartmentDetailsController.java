package com.example.Apartment.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Apartment.Service.ApartmentDetailsService;

/**
 * @author ARUN VEMIREDDY
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api")
public class ApartmentDetailsController {

	@Autowired
	private ApartmentDetailsService apartmentDetailsService;

	// fetch Apartment Details
	@GetMapping(path = "/getApartmentDetails")
	public ResponseEntity<?> getApartmentDetails() {
		ResponseEntity<?> response = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		response = new ResponseEntity<>(apartmentDetailsService.getApartmentDetails(), responseHeaders, HttpStatus.OK);
		return response;
	}

}
