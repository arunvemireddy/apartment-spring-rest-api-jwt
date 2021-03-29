package com.example.Apartment.Service;

import java.util.List;

import javax.xml.bind.ValidationException;

import org.springframework.stereotype.Service;

import com.example.Apartment.DTO.FlatsDTO;
import com.example.Apartment.DTO.OwnerDetailsDTO;
import com.example.Apartment.Entity.OwnerDetails;
import com.example.common.ResponsMessage;

@Service
public interface OwnerService {
	
	public List<OwnerDetails> getownerDetails();
	
	public String saveOwnerDetails(OwnerDetailsDTO detailsDTO) throws ValidationException;
	
	public List<Integer> searchOwnerDetails(FlatsDTO flatDto);
	
	public  List<OwnerDetails> selectOwnerDetails(FlatsDTO flatDto);
	
	public ResponsMessage updateOwnerDetails(OwnerDetailsDTO ownerDetailsDTO);

}
