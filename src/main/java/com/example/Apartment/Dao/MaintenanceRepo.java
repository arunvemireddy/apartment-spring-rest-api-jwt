package com.example.Apartment.Dao;

import com.example.Apartment.Entity.AuditMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author arun vemireddy
 */
@Repository
public interface MaintenanceRepo extends JpaRepository<AuditMaintenance,Integer> {

    @Query(value = "select date from AUDIT_MAINTENANCE where flat_no = ?1 and date between ?2 and ?3",nativeQuery = true)
    List<?> findByMaintenance(int flatno,String startDate,String endDate);
}
