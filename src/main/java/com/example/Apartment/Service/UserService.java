package com.example.Apartment.Service;

import com.example.Apartment.DTO.UserLoginDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author arun vemireddy
 */
@Service
public interface UserService {

    public String updateUserDetails(String userName, UserLoginDTO userLoginDTO);
    public Map<String,String> generateOTP(String key);
    public Boolean validateOtp(String key,String otp);
}
