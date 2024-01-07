package com.example.Apartment.ServiceImpl;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Apartment.Dao.UserRepository;
import com.example.Apartment.Entity.UserLogin;
import com.example.Apartment.Service.RegisterUserService;

/**
 * @author ARUN VEMIREDDY
 *
 */
@Service
public class RegisterUserServiceImpl implements RegisterUserService {

	public final static Logger log = LogManager.getLogger(RegisterUserServiceImpl.class);

	private UserRepository userRepository;

	private BCryptPasswordEncoder pwdencoder;

	@Autowired
	public RegisterUserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder pwdencoder) {
		this.userRepository = userRepository;
		this.pwdencoder = pwdencoder;
	}

	@Override
	public Integer saveUser(UserLogin user) {
		String username = user.getUsername();
		Optional<UserLogin> optionalUser = findByUsername(username);
		if (optionalUser.isEmpty()) {
			user.setPassword(pwdencoder.encode(user.getPassword()));
			return userRepository.save(user).getId();
		}
		return -1;
	}

	@Override
	public Optional<UserLogin> findByUsername(String userName) {
		return userRepository.findByUsername(userName);
	}

}
