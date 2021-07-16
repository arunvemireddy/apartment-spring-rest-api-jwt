package com.example.Apartment.Service;

import com.example.Apartment.DTO.AuditMaintenanceDTO;
import com.example.Apartment.Entity.AuditMaintenance;
import org.springframework.stereotype.Service;

/**
 * @author arun vemireddy
 */
@Service
public interface MaintanceService {

    public String payMaintenance(AuditMaintenanceDTO auditMaintenanceDTO);
}
