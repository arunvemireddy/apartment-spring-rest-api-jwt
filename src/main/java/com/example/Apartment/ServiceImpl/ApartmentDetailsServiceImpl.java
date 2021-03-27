package com.example.Apartment.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Apartment.Dao.ApartmentDetailsDao;
import com.example.Apartment.Entity.ApartmentDetails;
import com.example.Apartment.Service.ApartmentDetailsService;

@Service
public class ApartmentDetailsServiceImpl implements ApartmentDetailsService {
	
	@Autowired
	private ApartmentDetailsDao dao;

	@Override
	public List<ApartmentDetails> getApartmentDetails() {
		List<ApartmentDetails> apartmentDetails = new ArrayList<ApartmentDetails>();
		 apartmentDetails.addAll(dao.getApartmentDetails());
		 return apartmentDetails;
	}
}
