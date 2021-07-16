package com.example.Apartment.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

/**
 * @author arun vemireddy
 */
@Entity
public class AuditMaintenance {

    @Id
    private int flatno;
    private String ownerName;
    private String date;

    public int getFlatno() {
        return flatno;
    }

    public void setFlatno(int flatno) {
        this.flatno = flatno;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
