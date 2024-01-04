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

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder pwdencoder;

	@Override
	public Integer saveUser(UserLogin user) {
		String un = user.getUsername();
		Optional<UserLogin> username = findByUsername(un);

		if (username.isEmpty()) {
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
