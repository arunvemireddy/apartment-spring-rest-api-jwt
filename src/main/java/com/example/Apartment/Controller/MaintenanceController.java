package com.example.Apartment.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Apartment.Service.MaintanceService;

/**
 * @author arun vemireddy
 */
@RestController
@RequestMapping(path = "/api")
public class MaintenanceController {

	private MaintanceService maintanceService;

	@Autowired
	public MaintenanceController(MaintanceService maintanceService) {
		this.maintanceService = maintanceService;
	}

	@RequestMapping(path = "/payMaintenance", method = RequestMethod.GET)
	public ResponseEntity<?> payMaintenance(@RequestParam String flatNo) {
		int flat = Integer.parseInt(flatNo);
		return ResponseEntity.ok(maintanceService.payMaintenance(flat));
	}

	@RequestMapping(path = "/getOwnerMaintenanceDetails", method = RequestMethod.GET)
	public ResponseEntity<?> getOwnerMaintenanceDetails(@RequestParam String flatNo, @RequestParam String year) {
		int flat = Integer.parseInt(flatNo);
		return ResponseEntity.ok(maintanceService.payMaintenance(flat));
	}

	@RequestMapping(path = "/yearMaintenanceDetails", method = RequestMethod.GET)
	public ResponseEntity<?> yearMaintenanceDetails(@RequestParam String flatNo, @RequestParam String year) {
		return ResponseEntity.ok(maintanceService.getOwnerMaintenanceDetails(flatNo, year));
	}

}
