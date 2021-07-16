package com.example.Apartment.ServiceImpl;

import com.example.Apartment.DTO.UserLoginDTO;
import com.example.Apartment.Dao.UserRepository;
import com.example.Apartment.Entity.UserLogin;
import com.example.Apartment.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author arun vemireddy
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String updateUserDetails(String userName, UserLoginDTO userLoginDTO) {
        Optional<UserLogin> userLogin=userRepository.findByUsername(userName);
        UserLogin userLogin1=userLogin.get();
        userLogin1.setName(userLoginDTO.getEname());
        userLogin1.setRoles(userLoginDTO.getRoles());
        userRepository.save(userLogin1);
        return "updated";
    }
}
