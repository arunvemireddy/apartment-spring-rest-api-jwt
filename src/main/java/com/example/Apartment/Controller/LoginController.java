package com.example.Apartment.Controller;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.Apartment.DTO.UserRequest;
import com.example.Apartment.DTO.UserResponse;
import com.example.Apartment.Entity.UserLogin;
import com.example.Apartment.Service.ApartmentService;
import com.example.Apartment.Util.JwtUtil;
import com.example.common.ResponsMessage;
import com.fasterxml.jackson.core.JsonParseException;

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
	private JwtUtil util;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	    //validate user and generate token
		@PostMapping("/login")
		public ResponseEntity<UserResponse> login(@RequestBody UserRequest userRequest) {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUserName(),userRequest.getPassword()));
			String token=util.generatetoken(userRequest.getUserName());
			return ResponseEntity.ok(new UserResponse(token,"Success"));
		}
		
		//wrong Password exception handling
		@ExceptionHandler(BadCredentialsException.class)
		@ResponseStatus(HttpStatus.NOT_FOUND)
		  public ResponseEntity<?> handleNoSuchElementFoundException(BadCredentialsException exception) { 
			ResponseEntity<?> response = new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_GATEWAY);
		    return response;
		  }
		
		//wrong UserName exception handling
		@ExceptionHandler(InternalAuthenticationServiceException.class)
		@ResponseStatus(HttpStatus.NOT_FOUND)
		  public ResponseEntity<?> handleNoSuchElementFoundException(InternalAuthenticationServiceException exception) { 
			ResponseEntity<?> response = new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_GATEWAY);
		    return response;
		  }
		
	// save user in database - registration
	@PostMapping("/saveUser")
	public ResponseEntity<UserResponse> saveUser(@RequestBody UserLogin userLogin) {
		String body,message;
		int id=apartmentService.saveUser(userLogin);
		if(id==-1) {
			body="User Already Exist";
			message="Not Registered";
		}else {
			body="User '"+id+"'Registered";
			message="Registered";
		}
		return ResponseEntity.ok(new UserResponse(body,message));
	}
	
	//constraint exception handling
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	  public ResponseEntity<?> handleNoSuchElementFoundException(ConstraintViolationException exception) {
		ResponsMessage res = new ResponsMessage(exception.getConstraintViolations().stream().map(p->p.getMessageTemplate()).collect(Collectors.toList()).toString()); 
		ResponseEntity<?> response = new ResponseEntity<>(res,HttpStatus.BAD_GATEWAY);
	    return response;
	  }
	
	@ExceptionHandler(JsonParseException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	  public ResponseEntity<?> handleNoSuchElementFoundException(JsonParseException exception) {
		ResponsMessage res = new ResponsMessage(exception.getMessage().toString()); 
		ResponseEntity<?> response = new ResponseEntity<>(res,HttpStatus.BAD_GATEWAY);
	    return response;
	  }
	
	
}
