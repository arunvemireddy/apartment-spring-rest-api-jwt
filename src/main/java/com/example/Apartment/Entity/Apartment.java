package com.example.Apartment.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author ARUN VEMIREDDY
 *
 */
@Entity
public class Apartment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int apId;
	private String apartmentName;
	private int noofFlats;

	public int getApId() {
		return apId;
	}

	public void setApId(int apId) {
		this.apId = apId;
	}

	public String getApartmentName() {
		return apartmentName;
	}

	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}

	public int getNoofFlats() {
		return noofFlats;
	}

	public void setNoofFlats(int noofFlats) {
		this.noofFlats = noofFlats;
	}

}
