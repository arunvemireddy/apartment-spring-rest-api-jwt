package com.example.Apartment.DTO;

/**
 * @author arun vemireddy
 */
public class AuditMaintenanceDTO {

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
