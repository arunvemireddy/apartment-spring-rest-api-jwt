package com.example.Apartment.Controller;

import javax.xml.bind.ValidationException;

import com.example.Apartment.DTO.*;
import com.example.Apartment.Dao.OwnerDetailsDao;
import com.example.Apartment.Dao.UserRepository;
import com.example.Apartment.Entity.OwnerDetails;
import com.example.Apartment.Service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Apartment.Service.OwnerService;
import com.example.common.ResponsMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
	 * @author ARUN VEMIREDDY
	 *
	 */

@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping(path="/api")
public class OwnerController {

	Logger log= LogManager.getLogger(OwnerController.class);

	@Autowired
	private OwnerDetailsDao ownerDetailsDao;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OwnerService ownerService;

	@Autowired
	private UserService userService;
	
	@GetMapping(path="/getOwnerDetais")
	public ResponseEntity<?> getownerDetails(@RequestParam(required = false,defaultValue = "0") int pageNo,
											 @RequestParam(required = false,defaultValue = "1") int pageSize,
											 @RequestParam(required = false,defaultValue = "id") String colName){
		ResponseEntity<?> response= null;
		 HttpHeaders responseHeaders = new HttpHeaders();
		 log.info("pageNo"+pageNo+"pageSize"+pageSize);
		 Object[] object={ownerService.getownerDetails(pageNo,pageSize,colName),ownerDetailsDao.count()};
		 response = new ResponseEntity<>(object,responseHeaders,HttpStatus.OK);
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

	@GetMapping("/getOwnerName")
	public ResponseEntity<?> getOwnerName(){
		List<OwnerNameDTO> ownerNameDTOS=ownerDetailsDao.getOwnerName();
		return ResponseEntity.ok(ownerNameDTOS);
	}

	@RequestMapping(path = "/finduserByName",method = RequestMethod.GET)
	public ResponseEntity<?> finduserByName(@RequestParam String userName){
		return ResponseEntity.ok(userRepository.finduserDetails(userName));
	}

	@RequestMapping(path = "/updateuserByName",method = RequestMethod.POST)
	public ResponseEntity<?> updateuserByName(@RequestParam String userName,@RequestBody(required = false) UserLoginDTO userLoginDTO){
		return ResponseEntity.ok(userService.updateUserDetails(userName,userLoginDTO));
	}

	@RequestMapping(path = "/searchflatno",method = RequestMethod.GET)
	public ResponseEntity<List<OwnersProjections>> getOwnerByFlat(@RequestParam int flatno){
		return ResponseEntity.ok(ownerDetailsDao.getOwnersByFlat(flatno));
	}

	@RequestMapping(path = "/changeflatno",method = RequestMethod.GET)
	public ResponseEntity<List<OwnerListProjections>> getchangeflatno(@RequestParam int flatno){
		return ResponseEntity.ok(ownerDetailsDao.getOwnersListByFlat(flatno));
	}
}   
