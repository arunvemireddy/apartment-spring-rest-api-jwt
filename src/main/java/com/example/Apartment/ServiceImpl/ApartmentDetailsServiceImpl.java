package com.example.Apartment.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Apartment.Dao.ApartmentDetailsDao;
import com.example.Apartment.Entity.ApartmentDetails;
import com.example.Apartment.Service.ApartmentDetailsService;

/**
 * @author ARUN VEMIREDDY
 *
 */
@Service
public class ApartmentDetailsServiceImpl implements ApartmentDetailsService {

	@Autowired
	private ApartmentDetailsDao apartmentDetailsDao;

	@Override
	public List<ApartmentDetails> getApartmentDetails() {
		Optional<List<ApartmentDetails>> optionalApartment = apartmentDetailsDao.getApartmentDetails();
		List<ApartmentDetails> apartmentDetails = optionalApartment.get();
		return apartmentDetails;
	}
}
