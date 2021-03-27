package com.example.Apartment.Controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.Apartment.DTO.ApartmentDTO;
import com.example.Apartment.DTO.FlatsDTO;
import com.example.Apartment.DTO.LoginDTO;
import com.example.Apartment.DTO.OwnerDetailsDTO;
import com.example.Apartment.DTO.UserRequest;
import com.example.Apartment.DTO.UserResponse;
import com.example.Apartment.Dao.ApartmentDao;
import com.example.Apartment.Dao.ApartmentDetailsDao;
import com.example.Apartment.Entity.Apartment;
import com.example.Apartment.Entity.ApartmentDetails;
import com.example.Apartment.Entity.OwnerDetails;
import com.example.Apartment.Entity.UserLogin;
import com.example.Apartment.Service.ApartmentService;
import com.example.Apartment.Util.JwtUtil;
import com.example.common.ResponsMessage;
import com.example.common.ResponseStatus;


@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping(path="/api")
public class ApartmentController {
	@Autowired
    private ApartmentService apartmentService;
	
	@Autowired
	private ApartmentDao dao;
	
	@Autowired
	private ApartmentDetailsDao apartmentDetailsDao;
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	// validate user and generate token
		@PostMapping("/login")
		public ResponseEntity<UserResponse> login(@RequestBody UserRequest userRequest) {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUserName(),userRequest.getPassword()));
			String token=util.generatetoken(userRequest.getUserName());
			return ResponseEntity.ok(new UserResponse(token,"Success"));
		}
		
	//fetch Apartment Details
		@GetMapping(path="/getApartmentDetails")
		public ResponseEntity<?> getApartmentDetails(){
			ResponseEntity<?> response= null;
			 HttpHeaders responseHeaders = new HttpHeaders();
			 List<ApartmentDetails> apartmentDetails = new ArrayList<ApartmentDetails>();
			 apartmentDetails.addAll(apartmentDetailsDao.getApartmentDetails());
			 response = new ResponseEntity<>(apartmentDetails,HttpStatus.OK);
			 return response;
		}
		
	// show owner details	
		@GetMapping(path="/getOwnerDetais")
		public ResponseEntity<?> getownerDetails(){
			ResponseEntity<?> response= null;
			 HttpHeaders responseHeaders = new HttpHeaders();
			 List<OwnerDetails> details = new ArrayList<OwnerDetails>();
			 details.addAll(dao.fetchOwnerDetails());
			 response = new ResponseEntity<>(details,HttpStatus.OK);
			 return response;
		}
	
//	@PostMapping("save")
//	public ResponseEntity<?> savedetails(@RequestBody ApartmentDTO apartmentDTO) {
//		 HttpHeaders responseHeaders = new HttpHeaders();
//		 apartmentService.savedetails(apartmentDTO);
//		 return new ResponseEntity<String>("saved", responseHeaders, HttpStatus.CREATED);
//
//		 }
//	@GetMapping("get")
//	public List<ApartmentDTO> getdetails() {
//		return apartmentService.getdetails();
//	}
	
//	@GetMapping(path="/fetch")
//	public @ResponseBody List<Apartment> fetchdetails() {
//		 HttpHeaders responseHeaders = new HttpHeaders();
//		 System.out.println(apartmentService.getApdetails());
//		//return apartmentService.getApdetails();
//	//	 return dao.findAll();
//	}
	
//	@PutMapping(path="/update")
//	public ResponseEntity<?> updatedetails(@RequestBody Apartment apartment) {
//		
//		ResponseEntity<?> response = null;
//		 HttpHeaders responseHeaders = new HttpHeaders();
//           responseHeaders.add("content-type", "application/json;charset=UTF-8");
//		 System.out.println(apartmentService.getApdetails());
//		//return apartmentService.getApdetails();
//		 
//		//  response = new ResponseEntity<>(dao.save(apartment),responseHeaders,HttpStatus.OK);
//		  return response;
//	}
	
//	@DeleteMapping(path="/{apId}/delete")
//	public ResponseEntity<?> deleteDetails(@PathVariable("apId") int apId){
//		Long id = (long) apId;
//		ResponseEntity<?> response = null;
//		 HttpHeaders responseHeaders = new HttpHeaders();
//          responseHeaders.add("content-type", "application/json;charset=UTF-8");
//         apartmentService.delete(apId);
//          response = new ResponseEntity<>(responseHeaders,HttpStatus.OK);
//		return response;
//		
//	}
	
	@PostMapping("saveOwnerDetails")
	public ResponseEntity<Object> saveOwnerDetails(@RequestBody OwnerDetailsDTO detailsDTO) {
		int count=0;
		ResponseEntity<Object> response= null;
		 HttpHeaders responseHeaders = new HttpHeaders();
		 OwnerDetails ownerDetails = new OwnerDetails();
		 ownerDetails.setContact(detailsDTO.getContact());
		 List<Integer> flatList = dao.fetFlatno();
		 for (Integer integer : flatList) {
			if(integer==detailsDTO.getFlatno()) {
				count++;
			}
		}
		 if(count==0) {
		 ownerDetails.setFlatno(detailsDTO.getFlatno());
		 ownerDetails.setName(detailsDTO.getName());
		 dao.save(ownerDetails);
		 response=new ResponseEntity<Object>(new ResponsMessage("Owner Added"),responseHeaders, HttpStatus.OK);
		 }else {
		  response=new ResponseEntity<Object>(new ResponsMessage("FlatNo already had owner"), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		 }
		 return response;
		 }
	
	@PostMapping(path="/searchOwnerDetails")
	public ResponseEntity<?> searchOwnerDetails(@RequestBody FlatsDTO flatDto) {
		System.err.println("starts with");
		ResponseEntity<?> response = null;
		 HttpHeaders responseHeaders = new HttpHeaders();
         responseHeaders.add("content-type", "application/json;charset=UTF-8");
		 System.out.println(flatDto.getFlatno()+"starts");
		 int flatno=flatDto.getFlatno();
		 List<Integer> list=dao.fetchFlatno(flatno);
		 System.out.println(dao.fetchFlatno(flatDto.getFlatno()));
		 response=new ResponseEntity<Object>(list,responseHeaders, HttpStatus.OK);
		 return response;
	}
	
	@PostMapping(path="/selectOwnerDetails")
	public ResponseEntity<?> selectOwnerDetails(@RequestBody FlatsDTO flatDto) {
		System.err.println("starts with");
		ResponseEntity<?> response = null;
		 HttpHeaders responseHeaders = new HttpHeaders();
         responseHeaders.add("content-type", "application/json;charset=UTF-8");
		 System.out.println(flatDto.getFlatno()+"starts");
		 List<OwnerDetails> list=dao.getOwnerDetails(flatDto.getFlatno());
		 System.out.println(dao.fetchFlatno(flatDto.getFlatno()));
		 response=new ResponseEntity<Object>(list,responseHeaders, HttpStatus.OK);
		 return response;
	}
	
	@PostMapping(path="/updateOwnerDetails")
	public ResponseEntity<?> updateOwnerDetails(@RequestBody OwnerDetailsDTO ownerDetailsDTO) {
		ResponseEntity<?> responseEntity;
		OwnerDetails ownerDetails = new OwnerDetails();
		ownerDetails.setId(ownerDetailsDTO.getId());
		ownerDetails.setContact(ownerDetailsDTO.getContact());
		ownerDetails.setFlatno(ownerDetailsDTO.getFlatno());
		ownerDetails.setName(ownerDetailsDTO.getName());
		dao.save(ownerDetails);
		 HttpHeaders responseHeaders = new HttpHeaders();
		responseEntity= new ResponseEntity<Object>(new ResponsMessage("Updated"),responseHeaders,HttpStatus.OK);
		return responseEntity;	
	}
	
	// save user in database
	@PostMapping("/saveUser")
	public ResponseEntity<String> saveUser(@RequestBody UserLogin userLogin) {
		
		int id=apartmentService.saveUser(userLogin);
		String body="User '"+id+"'Saved";
		return ResponseEntity.ok(body);
	}
	
}
