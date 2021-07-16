package com.example.Apartment.ServiceImpl;

import com.example.Apartment.DTO.AuditMaintenanceDTO;
import com.example.Apartment.Dao.MaintenanceRepo;
import com.example.Apartment.Entity.AuditMaintenance;
import com.example.Apartment.Service.MaintanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * @author arun vemireddy
 */
@Service
public class MaintanceServiceImpl implements MaintanceService {

    @Autowired
    private MaintenanceRepo maintenanceRepo;


    @Override
    public String payMaintenance(AuditMaintenanceDTO auditMaintenanceDTO) {
        AuditMaintenance auditMaintenance1 = new AuditMaintenance();
        auditMaintenance1.setFlatno(auditMaintenanceDTO.getFlatno());
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        System.out.println(date);
        maintenanceRepo.save(auditMaintenance1);
        Optional<AuditMaintenance> optionalAuditMaintenance =maintenanceRepo.findById(auditMaintenanceDTO.getFlatno());
        AuditMaintenance auditMaintenance=optionalAuditMaintenance.get();
        String dstr=auditMaintenance.getDate()+","+date;
        System.out.println(dstr);
        auditMaintenance1.setDate(dstr);
        auditMaintenance1.setOwnerName(auditMaintenanceDTO.getOwnerName());
        maintenanceRepo.save(auditMaintenance1);
        return "payed";
    }
}
