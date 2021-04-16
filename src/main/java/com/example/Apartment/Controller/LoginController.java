package com.example.Apartment.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Apartment.DTO.UserRequest;
import com.example.Apartment.DTO.UserResponse;
import com.example.Apartment.Dao.ApartmentDetailsDao;
import com.example.Apartment.Entity.UserLogin;
import com.example.Apartment.Service.ApartmentService;
import com.example.Apartment.Util.JwtUtil;



	/**
	 * @author ARUN VEMIREDDY
	 *
	 */
@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping(path="/api")
public class LoginController {
	
	@Autowired
    private ApartmentService apartmentService;
	
	@Autowired
	private ApartmentDetailsDao apartmentDetailsDao;
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	// validate user and generate token
		@PostMapping("/login")
		public ResponseEntity<UserResponse> login(@RequestBody UserRequest userRequest) {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUserName(),userRequest.getPassword()));
			String token=util.generatetoken(userRequest.getUserName());
			return ResponseEntity.ok(new UserResponse(token,"Success"));
		}
		
	// save user in database
	@PostMapping("/saveUser")
	public ResponseEntity<String> saveUser(@RequestBody UserLogin userLogin) {
		int id=apartmentService.saveUser(userLogin);
		String body="User '"+id+"'Saved";
		return ResponseEntity.ok(body);
	}
	
}
