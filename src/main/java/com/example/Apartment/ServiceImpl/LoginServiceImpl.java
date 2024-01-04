package com.example.Apartment.ServiceImpl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Apartment.Dao.UserRepository;
import com.example.Apartment.Entity.UserLogin;
import com.example.Apartment.Service.LoginService;

@Service
public class LoginServiceImpl implements LoginService, UserDetailsService {

	private UserRepository userRepository;
	
	@Autowired
	public LoginServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Optional<UserLogin> findByUsername(String userName) {
		return userRepository.findByUsername(userName);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<UserLogin> user = findByUsername(userName);
		UserLogin userLogin = user.get();
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("User not Found Exception");
		}

		return new User(userName, userLogin.getPassword(), userLogin.getRoles().stream()
				.map((role -> new SimpleGrantedAuthority(role))).collect(Collectors.toList()));
	}

}
