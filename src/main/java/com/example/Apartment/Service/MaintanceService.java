package com.example.Apartment.Service;

import com.example.Apartment.DTO.AuditMaintenanceDTO;
import com.example.Apartment.Entity.AuditMaintenance;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author arun vemireddy
 */
@Service
public interface MaintanceService {

    public String payMaintenance(int flatNo);

    public List<?> getOwnerMaintenanceDetails(String flatNo, String year);
}
