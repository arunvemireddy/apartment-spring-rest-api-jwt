package com.example.Apartment.Dao;

import com.example.Apartment.Entity.AuditMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author arun vemireddy
 */
@Repository
public interface MaintenanceRepo extends JpaRepository<AuditMaintenance,Integer> {
}
