package com.example.Apartment.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.validation.ConstraintViolationException;

import com.example.Apartment.Dao.UserRepository;
import com.example.Apartment.Service.UserService;
import com.example.Apartment.ServiceImpl.EmailService;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.Apartment.DTO.UserRequest;
import com.example.Apartment.DTO.UserResponse;
import com.example.Apartment.Entity.UserLogin;
import com.example.Apartment.Service.ApartmentService;
import com.example.Apartment.Util.JwtUtil;
import com.example.common.ResponsMessage;
import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.web.util.NestedServletException;

/**
	 * @author ARUN VEMIREDDY
	 *
	 */
@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping(path="/api")
public class LoginController {

	public static final String APARTMENT_OTP_FOR_CHANGE_PASSWORD = "Apartment-OTP for change Password";

	@Autowired
    private ApartmentService apartmentService;
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	    //validate user and generate token
		@PostMapping(value = "/login" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<UserResponse> login(@RequestBody UserRequest userRequest) {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUserName(),userRequest.getPassword()));
			String token=util.generatetoken(userRequest.getUserName());
			String userName=userRequest.getUserName();
			return ResponseEntity.ok(new UserResponse(token,userName));
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
	@PostMapping(value = "/saveUser" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
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

		@GetMapping("/generateOtp")
		public ResponseEntity<?> generateOTP(@RequestParam(required = false) String username) throws MessagingException{
			String email=null;
			String otp=null;
			Map<String,String> map = new HashMap<>();
				try {
					map = userService.generateOTP(username);
					email=map.get("email");
					otp = map.get("otp");
					emailService.sendOtpMessage(email, APARTMENT_OTP_FOR_CHANGE_PASSWORD, otp);
					ResponsMessage message= new ResponsMessage("OTP Sent Successfully");
					return new ResponseEntity<>(message,HttpStatus.OK);
				}catch (Exception e){
					ResponsMessage message= new ResponsMessage("Failed to send OTP");
					return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
				}
		}

		@GetMapping(path = "/validateOtp")
		public ResponseEntity<?> validateOtp(@RequestParam() String username,@RequestParam() String otp){

			boolean b=userService.validateOtp(username,otp);
			if(b==true){
				ResponsMessage responsMessage = new ResponsMessage("OTP Validated");
				return new ResponseEntity<>(responsMessage,HttpStatus.OK);
			}else{
				ResponsMessage responsMessage = new ResponsMessage("OTP is Invalid");
				return new ResponseEntity<>(responsMessage,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	@GetMapping(path = "/changepassword")
	public ResponseEntity<?> changepassword(@RequestParam() String username,@RequestParam String password){
		Optional<UserLogin> userLogin= userRepository.findByUsername(username);
		UserLogin login=userLogin.get();
		login.setPassword(bCryptPasswordEncoder.encode(password));
		userRepository.save(login);
		ResponsMessage responsMessage = new ResponsMessage("Password changed Successfully");
		return new ResponseEntity<>(responsMessage,HttpStatus.OK);
	}
}
