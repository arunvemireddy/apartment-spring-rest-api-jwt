package com.example.Apartment.ServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.ValidationException;

import com.example.Apartment.Helper.OwnerCSVDownload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Apartment.DTO.FlatsDTO;
import com.example.Apartment.DTO.OwnerDetailsDTO;
import com.example.Apartment.Dao.OwnerDetailsDao;
import com.example.Apartment.Entity.OwnerDetails;
import com.example.Apartment.Service.OwnerService;
import com.example.common.ResponsMessage;
import org.springframework.web.multipart.MultipartFile;

/**
	 * @author ARUN VEMIREDDY
	 *
	 */
@Service
public class OwnerServiceImpl implements OwnerService {
	
	@Autowired
	private OwnerDetailsDao ownerDetailsDao;

	@Override
	public Page<OwnerDetails> getownerDetails(int pageNo, int pageSize, String colName) {
		 Pageable pageable= PageRequest.of(pageNo,pageSize, Sort.by(colName));
		 return ownerDetailsDao.findAll(pageable);
	}
	
	@Override
	public String saveOwnerDetails(OwnerDetailsDTO detailsDTO) throws ValidationException {
		String res="";
		int count=0;
		 OwnerDetails ownerDetails = new OwnerDetails();
		 List<Integer> flatList = ownerDetailsDao.fetFlatno(detailsDTO.getFlatno());
		 System.err.println(ownerDetailsDao.fetFlatno(detailsDTO.getFlatno()));
		 for (Integer integer : flatList) {
			if(integer>0) {
				count++;
			}
		}
		 if(count>0) {
			 throw new ValidationException("FlatNo already has a owner");
		 }else if(count==0) {
			 ownerDetails.setFlatno(detailsDTO.getFlatno());
			 ownerDetails.setName(detailsDTO.getName());
			 ownerDetails.setContact(detailsDTO.getContact());
			 ownerDetailsDao.save(ownerDetails);
			 res="Owner Added";
			 return res;
		 }
		return res;
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
		Optional<OwnerDetails> optionalOwnerDetails= ownerDetailsDao.findById(ownerDetailsDTO.getId());
		OwnerDetails ownerDetails=optionalOwnerDetails.get();
		ownerDetails.setContact(ownerDetailsDTO.getContact());
		ownerDetails.setFlatno(ownerDetailsDTO.getFlatno());
		ownerDetails.setName(ownerDetailsDTO.getName());
		ownerDetailsDao.save(ownerDetails);
		ResponsMessage responsMessage = new ResponsMessage("Owner Details Updated");
		return responsMessage;	
	}

	@Override
	public ByteArrayInputStream downloadOwnerFile() {
		List<OwnerDetails> ownerDetails = ownerDetailsDao.findAll();
		ByteArrayInputStream in = OwnerCSVDownload.tutorialsToCSV(ownerDetails);
		return in;
	}

	@Override
	public String uploadProfilePic(MultipartFile file,String id){
	Optional<OwnerDetails> optionalOwnerDetails= ownerDetailsDao.findById(Integer.parseInt(id));
	OwnerDetails ownerDetails = optionalOwnerDetails.get();
		try {
			ownerDetails.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
			ownerDetailsDao.save(ownerDetails);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "uploaded";
	}

	@Override
	public String getProfilePic(String id){
		Optional<OwnerDetails> optionalOwnerDetails= ownerDetailsDao.findById(Integer.parseInt(id));
		OwnerDetails ownerDetails = optionalOwnerDetails.get();
		return ownerDetails.getImage();
	}


}
