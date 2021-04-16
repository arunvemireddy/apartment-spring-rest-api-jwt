package com.example.Apartment.Controller;

import javax.xml.bind.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Apartment.DTO.FlatsDTO;
import com.example.Apartment.DTO.OwnerDetailsDTO;
import com.example.Apartment.Service.OwnerService;
import com.example.common.ResponsMessage;
import com.example.common.ResponseStatus;


	/**
	 * @author ARUN VEMIREDDY
	 *
	 */

@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping(path="/api")
public class OwnerController {
	
	@Autowired
	private OwnerService ownerService;
	
	@GetMapping(path="/getOwnerDetais")
	public ResponseEntity<?> getownerDetails(){
		ResponseEntity<?> response= null;
		 HttpHeaders responseHeaders = new HttpHeaders();
		 response = new ResponseEntity<>(ownerService.getownerDetails(),responseHeaders,HttpStatus.OK);
		 return response;
	}
	
	@PostMapping("saveOwnerDetails")
	public ResponseEntity<Object> saveOwnerDetails(@RequestBody OwnerDetailsDTO detailsDTO)  {
		 ResponseEntity<Object> response= null;
		 HttpHeaders responseHeaders = new HttpHeaders(); 
		 String res;
	
			try {
				res = ownerService.saveOwnerDetails(detailsDTO);
				if(StringUtils.isNoneEmpty(res)) {
					 ResponsMessage responsMessage = new ResponsMessage(res);
					 response=new ResponseEntity<Object>(responsMessage,responseHeaders, HttpStatus.OK); 
			}} catch (ValidationException e) {
				 ResponsMessage responsMessage = new ResponsMessage(e.getMessage());
				 response=new ResponseEntity<Object>(responsMessage,responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR); 
			}
		 return response;
		 }  
	
	@PostMapping(path="/searchOwnerDetails")
	public ResponseEntity<?> searchOwnerDetails(@RequestBody FlatsDTO flatDto) {
		 ResponseEntity<?> response = null;
		 HttpHeaders responseHeaders = new HttpHeaders();
         responseHeaders.add("content-type", "application/json;charset=UTF-8");
		 response=new ResponseEntity<Object>(ownerService.searchOwnerDetails(flatDto),responseHeaders, HttpStatus.OK);
		 return response;
	}
	
	@PostMapping(path="/selectOwnerDetails")
	public ResponseEntity<?> selectOwnerDetails(@RequestBody FlatsDTO flatDto) {
		 ResponseEntity<?> response = null;
		 HttpHeaders responseHeaders = new HttpHeaders();
         responseHeaders.add("content-type", "application/json;charset=UTF-8");
		 response=new ResponseEntity<Object>(ownerService.selectOwnerDetails(flatDto),responseHeaders, HttpStatus.OK);
		 return response;
	}
	
	@PostMapping(path="/updateOwnerDetails")
	public ResponseEntity<?> updateOwnerDetails(@RequestBody OwnerDetailsDTO ownerDetailsDTO) {
		ResponseEntity<?> responseEntity;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseEntity= new ResponseEntity<Object>(ownerService.updateOwnerDetails(ownerDetailsDTO),responseHeaders,HttpStatus.OK);
		return responseEntity;	
	}

}
