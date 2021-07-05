package com.example.Apartment.Service;

import java.awt.print.Pageable;
import java.util.List;

import javax.xml.bind.ValidationException;

import org.springframework.stereotype.Service;

import com.example.Apartment.DTO.FlatsDTO;
import com.example.Apartment.DTO.OwnerDetailsDTO;
import com.example.Apartment.Entity.OwnerDetails;
import com.example.common.ResponsMessage;

	/**
	 * @author ARUN VEMIREDDY
	 *
	 */
@Service
public interface OwnerService {
	
	public List<OwnerDetails> getownerDetails(int pageNo,int pageSize,String colName);
	
	public String saveOwnerDetails(OwnerDetailsDTO detailsDTO) throws ValidationException;
	
	public List<Integer> searchOwnerDetails(FlatsDTO flatDto);
	
	public  List<OwnerDetails> selectOwnerDetails(FlatsDTO flatDto);
	
	public ResponsMessage updateOwnerDetails(OwnerDetailsDTO ownerDetailsDTO);

}
