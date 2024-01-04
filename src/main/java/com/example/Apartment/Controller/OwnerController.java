package com.example.Apartment.Controller;

import java.util.List;

import javax.xml.bind.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Apartment.DTO.FlatsDTO;
import com.example.Apartment.DTO.OwnerDetailsDTO;
import com.example.Apartment.DTO.OwnerNameDTO;
import com.example.Apartment.DTO.UserLoginDTO;
import com.example.Apartment.Dao.OwnerDetailsDao;
import com.example.Apartment.Dao.UserRepository;
import com.example.Apartment.Entity.OwnerDetails;
import com.example.Apartment.Service.OwnerService;
import com.example.Apartment.Service.UserService;
import com.example.common.ResponsMessage;

import io.jsonwebtoken.SignatureException;

/**
 * @author ARUN VEMIREDDY
 *
 */

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api")
public class OwnerController {

	private static final Logger log = LogManager.getLogger(OwnerController.class);

	private OwnerDetailsDao ownerDetailsDao;

	private UserRepository userRepository;

	private OwnerService ownerService;

	private UserService userService;

	@Autowired
	public OwnerController(OwnerDetailsDao ownerDetailsDao, UserRepository userRepository, OwnerService ownerService,
			UserService userService) {
		this.ownerDetailsDao = ownerDetailsDao;
		this.userRepository = userRepository;
		this.ownerService = ownerService;
		this.userService = userService;
	}

	@GetMapping(path = "/getOwnerDetails")
	public ResponseEntity<?> getOwnerDetails(
	        @RequestParam(required = false, defaultValue = "0") int pageNo,
	        @RequestParam(required = false, defaultValue = "10") int pageSize,
	        @RequestParam(required = false, defaultValue = "id") String colName) {
	    try {
	        Page<OwnerDetails> page = ownerService.getownerDetails(pageNo, pageSize, colName);
	        return new ResponseEntity<>(page, HttpStatus.OK);
	    } catch (SignatureException e) {
	        log.error("Error parsing token claims: " + e.getMessage());
	        return new ResponseEntity<>("Invalid token. Please log in again.", HttpStatus.UNAUTHORIZED);
	    } catch (Exception e) {
	        log.error("Error getting owner details: " + e.getMessage());
	        return new ResponseEntity<>("Error getting owner details, login again", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	@PostMapping("saveOwnerDetails")
	public ResponseEntity<Object> saveOwnerDetails(@RequestBody OwnerDetailsDTO detailsDTO) {
		ResponseEntity<Object> response = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		String res;

		try {
			res = ownerService.saveOwnerDetails(detailsDTO);
			if (StringUtils.isNoneEmpty(res)) {
				ResponsMessage responsMessage = new ResponsMessage(res);
				response = new ResponseEntity<Object>(responsMessage, responseHeaders, HttpStatus.OK);
			}
		} catch (ValidationException e) {
			ResponsMessage responsMessage = new ResponsMessage(e.getMessage());
			response = new ResponseEntity<Object>(responsMessage, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@PostMapping(path = "/searchOwnerDetails")
	public ResponseEntity<?> searchOwnerDetails(@RequestBody FlatsDTO flatDto) {
		ResponseEntity<?> response = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("content-type", "application/json;charset=UTF-8");
		response = new ResponseEntity<Object>(ownerService.searchOwnerDetails(flatDto), responseHeaders, HttpStatus.OK);
		return response;
	}

	@PostMapping(path = "/selectOwnerDetails")
	public ResponseEntity<?> selectOwnerDetails(@RequestBody FlatsDTO flatDto) {
		ResponseEntity<?> response = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("content-type", "application/json;charset=UTF-8");
		response = new ResponseEntity<Object>(ownerService.selectOwnerDetails(flatDto), responseHeaders, HttpStatus.OK);
		return response;
	}

	@PostMapping(path = "/updateOwnerDetails")
	public ResponseEntity<?> updateOwnerDetails(@RequestBody OwnerDetailsDTO ownerDetailsDTO) {
		ResponseEntity<?> responseEntity;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseEntity = new ResponseEntity<Object>(ownerService.updateOwnerDetails(ownerDetailsDTO), responseHeaders,
				HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping("/getOwnerName")
	public ResponseEntity<?> getOwnerName() {
		List<OwnerNameDTO> ownerNameDTOS = ownerDetailsDao.getOwnerName();
		return ResponseEntity.ok(ownerNameDTOS);
	}

	@RequestMapping(path = "/finduserByName", method = RequestMethod.GET)
	public ResponseEntity<?> finduserByName(@RequestParam String userName) {
		return ResponseEntity.ok(userRepository.finduserDetails(userName));
	}

	@RequestMapping(path = "/updateuserByName", method = RequestMethod.POST)
	public ResponseEntity<?> updateuserByName(@RequestParam String userName,
			@RequestBody(required = false) UserLoginDTO userLoginDTO) {
		return ResponseEntity.ok(userService.updateUserDetails(userName, userLoginDTO));
	}

	@RequestMapping(path = "/searchflatno", method = RequestMethod.GET)
	public ResponseEntity<List<Integer>> getOwnerByFlat(@RequestParam int flatno) {
		return ResponseEntity.ok(ownerDetailsDao.getOwnersByFlat(flatno));
	}

	@RequestMapping(path = "/changeflatno", method = RequestMethod.GET)
	public ResponseEntity<?> getchangeflatno(@RequestParam int flatno) {
		return new ResponseEntity<>(ownerDetailsDao.getOwnersListByFlat(flatno), HttpStatus.OK);
	}

	@GetMapping(path = "/downloadownerTable")
	public ResponseEntity<?> downloadownerTable() {
		String filename = "tutorials.csv";
		InputStreamResource file = new InputStreamResource(ownerService.downloadOwnerFile());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
//		return  ResponseEntity.ok(ownerDetailsDao.findAll());
	}

	@PostMapping(path = "/uploadProfilePic")
	public ResponseEntity<?> uploadProfilePic(@RequestBody MultipartFile file, @RequestParam() String id) {
		ResponseEntity<?> responseEntity;
		HttpHeaders responseHeaders = new HttpHeaders();
		ownerService.uploadProfilePic(file, id);
		responseEntity = new ResponseEntity<Object>(ownerService.getProfilePic(id), responseHeaders, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping(path = "/getProfilePic")
	public ResponseEntity<?> getProfilePic(@RequestParam() String id) {
		ResponseEntity<?> responseEntity;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseEntity = new ResponseEntity<Object>(ownerService.getProfilePic(id), responseHeaders, HttpStatus.OK);
		return responseEntity;
	}

}
