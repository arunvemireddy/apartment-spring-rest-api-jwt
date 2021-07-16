package com.example.Apartment.Service;

import com.example.Apartment.DTO.UserLoginDTO;
import org.springframework.stereotype.Service;

/**
 * @author arun vemireddy
 */
@Service
public interface UserService {

    public String updateUserDetails(String userName, UserLoginDTO userLoginDTO);
}
