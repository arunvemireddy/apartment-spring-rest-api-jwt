package com.example.Apartment.ServiceImpl;

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
import com.example.Apartment.DTO.OwnerDetailsDTO;
import com.example.Apartment.Dao.ApartmentDao;
import com.example.Apartment.Dao.UserRepository;
import com.example.Apartment.Entity.Apartment;
import com.example.Apartment.Entity.OwnerDetails;
import com.example.Apartment.Entity.UserLogin;
import com.example.Apartment.Service.ApartmentService;

@Service
public class ApartmentServiceImpl implements ApartmentService,UserDetailsService {

	@Autowired
	private ApartmentDao apartmentdao;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder pwdencoder;

//	@Override
//	public void savedetails(ApartmentDTO apartmentDTO) {
//		// TODO Auto-generated method stub
//		Apartment apartment = new Apartment();
//		apartment.setApartmentName(apartmentDTO.getApartmentName());
//		apartment.setNoofFlats(apartmentDTO.getNoofFlats());
//		//apartmentdao.save(apartment);
//	}

//	@Override
//	public List<ApartmentDTO> getdetails() {
//		// TODO Auto-generated method stub
//		List<ApartmentDTO> ap = apartmentdao.fetchDetails();
//		return ap;
//	}
	

//	@Override
//	public List<Apartment> getApdetails() {
//		// TODO Auto-generated method stub
//		List<Apartment> ap = apartmentdao.getApDetails();
//		return ap;
//	}

//	@Override
//	public void delete(int id) {
//		// TODO Auto-generated method stub
//		apartmentdao.delete(id);
//	}

	@Override
	public List<String> getownerDetails() {
		// TODO Auto-generated method stub
		
		OwnerDetailsDTO detailsDTO = new OwnerDetailsDTO();
		List<String> details = apartmentdao.getOwnerDetails();
		return details;
	}

	@Override
	public List<OwnerDetails> fetchownerDetails() {
		// TODO Auto-generated method stub
		///List<OwnerDetails> details = apartmentdao.fetchOwnerDetails();
		return null;
	}

	@Override
	public Integer saveUser(UserLogin user) {
		// TODO Auto-generated method stub
		user.setPassword(pwdencoder.encode(user.getPassword()));
		return userRepository.save(user).getId();
	}

	@Override
	public Optional<UserLogin> findByUsername(String userName) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(userName);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<UserLogin> user=findByUsername(userName);
		
		UserLogin userLogin = user.get();
		 
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("User not Found Exception");
		}
		return new User(userName, userLogin.getPassword(), userLogin.getRoles().stream()
				.map((role->new SimpleGrantedAuthority(role)))
				.collect(Collectors.toList()));
	}
	
	

//	@Override
//	public List<ApartmentDTO> getdetails() {
//		// TODO Auto-generated method stub
//
//		List<Apartment> apartmentDTO = apartmentdao.findAll();
//		List<ApartmentDTO> ap = apartmentdao.fetchDetails();
//		
//	}
	
	
}
