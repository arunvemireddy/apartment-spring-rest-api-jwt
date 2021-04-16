package com.example.Apartment.ServiceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Apartment.DTO.ApartmentDTO;
import com.example.Apartment.DTO.ApartmentDetailsDTO;
import com.example.Apartment.DTO.OwnerDetailsDTO;
import com.example.Apartment.Dao.OwnerDetailsDao;
import com.example.Apartment.Dao.UserRepository;
import com.example.Apartment.Entity.Apartment;
import com.example.Apartment.Entity.ApartmentDetails;
import com.example.Apartment.Entity.OwnerDetails;
import com.example.Apartment.Entity.UserLogin;
import com.example.Apartment.Service.ApartmentService;

	/**
	 * @author ARUN VEMIREDDY
	 *
	 */
@Service
public class ApartmentServiceImpl implements ApartmentService,UserDetailsService {

	@Autowired
	private OwnerDetailsDao apartmentdao;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder pwdencoder;

	@Override
	public List<OwnerDetails> fetchownerDetails() {
		///List<OwnerDetails> details = apartmentdao.fetchOwnerDetails();
		return null;
	}

	@Override
	public Integer saveUser(UserLogin user) {
		user.setPassword(pwdencoder.encode(user.getPassword()));
		return userRepository.save(user).getId();
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
