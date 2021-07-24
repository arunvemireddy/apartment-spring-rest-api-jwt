package com.example.Apartment.ServiceImpl;

import com.example.Apartment.DTO.AuditMaintenanceDTO;
import com.example.Apartment.Dao.MaintenanceRepo;
import com.example.Apartment.Dao.OwnerDetailsDao;
import com.example.Apartment.Entity.AuditMaintenance;
import com.example.Apartment.Entity.OwnerDetails;
import com.example.Apartment.Service.MaintanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author arun vemireddy
 */
@Service
public class MaintanceServiceImpl implements MaintanceService {

    @Autowired
    private MaintenanceRepo maintenanceRepo;

    @Autowired
    private OwnerDetailsDao ownerDetailsDao;


    @Override
    public String payMaintenance(int flatNo) {
        OwnerDetails ownerDetails= ownerDetailsDao.findByflatno(flatNo);
        AuditMaintenance auditMaintenance = new AuditMaintenance();
        auditMaintenance.setOwnerName(ownerDetails.getName());
        auditMaintenance.setFlatNo(ownerDetails.getFlatno());
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        auditMaintenance.setDate(LocalDate.parse(date));
        maintenanceRepo.save(auditMaintenance);
        return "payed";
    }

    @Override
    public List<?> getOwnerMaintenanceDetails(String flatNo, String year) {
        int fno=Integer.parseInt(flatNo);
        String sdate=year+"-01-01";
        String edate=year+"-12-30";
        System.out.println(edate);
        System.out.println(sdate);
        int fNo=Integer.parseInt(flatNo);
        System.out.println(fNo);
        List<?> list=maintenanceRepo.findByMaintenance(fNo,sdate,edate);
        System.out.println(list);
        return list;
    }
}
