package com.example.Apartment.Service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.Apartment.DTO.UserLoginDTO;

/**
 * @author arun vemireddy
 */
@Service
public interface UserService {

	public String updateUserDetails(String userName, UserLoginDTO userLoginDTO);

	public Map<String, String> generateOTP(String key);

	public Boolean validateOtp(String key, String otp);
}
