package com.example.Apartment.Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Apartment.Entity.UserLogin;
import com.example.Apartment.Service.RegisterUserService;
import com.example.Apartment.constants.UserConstants;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api")
public class RegisterUserController {
	
	private static final Logger log= LogManager.getLogger(RegisterUserController.class);

	@Autowired
	private RegisterUserService registerUserService;

	// save user in database - registration
	@PostMapping(value = "/saveUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveUser(@RequestBody UserLogin userLogin) {
		int id = registerUserService.saveUser(userLogin);
		String message = (id == -1) ? UserConstants.USER_ALREADY_EXISTS
				: String.format(UserConstants.USER_REGISTERED_FORMAT, id);
		return ResponseEntity.ok((message + "," + getResponseStatus(id)));
	}

	private String getResponseStatus(int id) {
		return (id == -1) ? UserConstants.NOT_REGISTERED : UserConstants.REGISTERED;
	}

}
