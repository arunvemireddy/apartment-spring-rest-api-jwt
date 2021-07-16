package com.example.Apartment.Controller;

import com.example.Apartment.DTO.AuditMaintenanceDTO;
import com.example.Apartment.Entity.AuditMaintenance;
import com.example.Apartment.Service.MaintanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author arun vemireddy
 */
@RestController
@RequestMapping(path = "/api")
public class MaintenanceController {

    @Autowired
    private MaintanceService maintanceService;

    @RequestMapping(path = "/payMaintenance",method = RequestMethod.POST)
    public ResponseEntity<?> payMaintenance(@RequestBody AuditMaintenanceDTO auditMaintenanceDTO){
        return ResponseEntity.ok(maintanceService.payMaintenance(auditMaintenanceDTO));
    }
}
