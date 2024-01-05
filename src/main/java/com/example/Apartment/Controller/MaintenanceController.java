package com.example.Apartment.Controller;

import com.example.Apartment.DTO.AuditMaintenanceDTO;
import com.example.Apartment.Entity.AuditMaintenance;
import com.example.Apartment.Service.MaintanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author arun vemireddy
 */
@RestController
@RequestMapping(path = "/api")
public class MaintenanceController {

	@Autowired
	private MaintanceService maintanceService;

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
