package com.example.Apartment.ServiceImpl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Apartment.Dao.UserRepository;
import com.example.Apartment.Entity.UserLogin;
import com.example.Apartment.Service.ApartmentService;

	/**
	 * @author ARUN VEMIREDDY
	 *
	 */
@Service
public class ApartmentServiceImpl implements ApartmentService,UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder pwdencoder;


	@Override
	public Integer saveUser(UserLogin user){
		String un = user.getUsername();
		Optional<UserLogin> username=findByUsername(un);
		
		if(username.isEmpty()) {
			user.setPassword(pwdencoder.encode(user.getPassword()));
			return userRepository.save(user).getId();
		}
		return -1;
	}

	@Override
	public Optional<UserLogin> findByUsername(String userName) {
		return userRepository.findByUsername(userName);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {		
		Optional<UserLogin> user=findByUsername(userName);
		UserLogin userLogin = user.get();
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("User not Found Exception");
		}
		return new User(userName, userLogin.getPassword(), userLogin.getRoles().stream()
				.map((role->new SimpleGrantedAuthority(role)))
				.collect(Collectors.toList()));
	}
}
