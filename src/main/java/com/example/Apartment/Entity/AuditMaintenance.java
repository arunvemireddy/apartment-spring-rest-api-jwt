package com.example.Apartment.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

/**
 * @author arun vemireddy
 */
@Entity
public class AuditMaintenance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int flatNo;
	private String ownerName;
	private LocalDate date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(int flatNo) {
		this.flatNo = flatNo;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
