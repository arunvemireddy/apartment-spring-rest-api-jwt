package com.example.Apartment.Service;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @author arun vemireddy
 */
@Service
public interface MaintanceService {

	public String payMaintenance(int flatNo);

	public List<?> getOwnerMaintenanceDetails(String flatNo, String year);
}
