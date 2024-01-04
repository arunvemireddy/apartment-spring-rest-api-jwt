package com.example.Apartment.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Apartment.DTO.UserRequest;
import com.example.Apartment.DTO.UserResponse;
import com.example.Apartment.Dao.UserRepository;
import com.example.Apartment.Entity.UserLogin;
import com.example.Apartment.Service.UserService;
import com.example.Apartment.ServiceImpl.EmailService;
import com.example.Apartment.Util.JwtUtil;
import com.example.common.ResponsMessage;

/**
 * @author ARUN VEMIREDDY
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api")
public class LoginController {

	public static final String APARTMENT_OTP_FOR_CHANGE_PASSWORD = "Apartment-OTP for change Password";

	private JwtUtil util;

	private AuthenticationManager authenticationManager;

	private EmailService emailService;

	private UserService userService;

	private UserRepository userRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Autowired
	public LoginController(JwtUtil util, AuthenticationManager authenticationManager, EmailService emailService,
			UserService userService, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.util = util;
		this.authenticationManager = authenticationManager;
		this.emailService = emailService;
		this.userService = userService;
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	

	// validate user and generate token
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody UserRequest userRequest, HttpServletResponse response) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
		String token = util.generatetoken(userRequest.getUsername());
		String username = userRequest.getUsername();
		
//		Cookie myCookie = new Cookie("myCookieName", "cookieValue");
//		myCookie.setMaxAge(3600);  // Cookie will expire after 1 hour (in seconds)
//		myCookie.setPath("/");
//		response.addCookie(myCookie);
//		response.addHeader("token", token);
//		response.setContentType("")
		
		return ResponseEntity.ok(new UserResponse(token, username));
	}

	@GetMapping("/generateOtp")
	public ResponseEntity<?> generateOTP(@RequestParam(required = false) String username) throws MessagingException {
		String email = null;
		String otp = null;
		Map<String, String> map = new HashMap<>();
		try {
			map = userService.generateOTP(username);
			email = map.get("email");
			otp = map.get("otp");
			emailService.sendOtpMessage(email, APARTMENT_OTP_FOR_CHANGE_PASSWORD, otp);
			ResponsMessage message = new ResponsMessage("OTP Sent Successfully");
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (Exception e) {
			ResponsMessage message = new ResponsMessage("Failed to send OTP");
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/validateOtp")
	public ResponseEntity<?> validateOtp(@RequestParam() String username, @RequestParam() String otp) {

		boolean b = userService.validateOtp(username, otp);
		if (b == true) {
			ResponsMessage responsMessage = new ResponsMessage("OTP Validated");
			return new ResponseEntity<>(responsMessage, HttpStatus.OK);
		} else {
			ResponsMessage responsMessage = new ResponsMessage("OTP is Invalid");
			return new ResponseEntity<>(responsMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/changepassword")
	public ResponseEntity<?> changepassword(@RequestParam() String username, @RequestParam String password) {
		Optional<UserLogin> userLogin = userRepository.findByUsername(username);
		UserLogin login = userLogin.get();
		login.setPassword(bCryptPasswordEncoder.encode(password));
		userRepository.save(login);
		ResponsMessage responsMessage = new ResponsMessage("Password changed Successfully");
		return new ResponseEntity<>(responsMessage, HttpStatus.OK);
	}
}
