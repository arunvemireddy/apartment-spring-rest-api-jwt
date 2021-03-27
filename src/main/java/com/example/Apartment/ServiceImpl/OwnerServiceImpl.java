package com.example.Apartment.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.Apartment.DTO.FlatsDTO;
import com.example.Apartment.DTO.OwnerDetailsDTO;
import com.example.Apartment.Dao.OwnerDetailsDao;
import com.example.Apartment.Entity.OwnerDetails;
import com.example.Apartment.Service.OwnerService;
import com.example.common.ResponsMessage;

@Service
public class OwnerServiceImpl implements OwnerService {
	
	@Autowired
	private OwnerDetailsDao ownerDetailsDao;

	@Override
	public List<OwnerDetails> getownerDetails() {
		OwnerDetailsDTO detailsDTO = new OwnerDetailsDTO();
		 List<OwnerDetails> details = new ArrayList<OwnerDetails>();
		 details.addAll(ownerDetailsDao.fetchOwnerDetails());
		 return details;
	}
	
	@Override
	public ResponsMessage saveOwnerDetails(OwnerDetailsDTO detailsDTO) {
		String res;
		int count=0;
		 OwnerDetails ownerDetails = new OwnerDetails();
		 List<Integer> flatList = ownerDetailsDao.fetFlatno();
		 for (Integer integer : flatList) {
			if(integer==detailsDTO.getFlatno()) {
				count++;
			}
		}
		 if(count==0) {
		 ownerDetails.setFlatno(detailsDTO.getFlatno());
		 ownerDetails.setName(detailsDTO.getName());
		 ownerDetails.setContact(detailsDTO.getContact());
		 ownerDetailsDao.save(ownerDetails);
		 res="Owner Added";
		 }else {
	     res="FlatNo already had owner";
		 }
		 ResponsMessage responsMessage = new ResponsMessage(res);
		 return responsMessage;
		 }

	@Override
	public List<Integer> searchOwnerDetails(FlatsDTO flatDto) {
		 int flatno=flatDto.getFlatno();
		 List<Integer> list=ownerDetailsDao.fetchFlatno(flatno);
		 return list;
	}
	
	@Override
	public  List<OwnerDetails> selectOwnerDetails(FlatsDTO flatDto) {
		 int flatno=flatDto.getFlatno();
		 List<OwnerDetails> list=ownerDetailsDao.getOwnerDetails(flatno);
		 return list;
	}
	
	@Override
	public ResponsMessage updateOwnerDetails(OwnerDetailsDTO ownerDetailsDTO) {
		OwnerDetails ownerDetails = new OwnerDetails();
		ownerDetails.setId(ownerDetailsDTO.getId());
		ownerDetails.setContact(ownerDetailsDTO.getContact());
		ownerDetails.setFlatno(ownerDetailsDTO.getFlatno());
		ownerDetails.setName(ownerDetailsDTO.getName());
		ownerDetailsDao.save(ownerDetails);
		ResponsMessage responsMessage = new ResponsMessage("Owner Details Updated");
		return responsMessage;	
	}
}
