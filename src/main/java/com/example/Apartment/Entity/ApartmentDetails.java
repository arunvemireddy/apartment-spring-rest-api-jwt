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
public class ApartmentDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	private String aptName;
	private String aptAddress;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getAptName() {
		return aptName;
	}
	public void setAptName(String aptName) {
		this.aptName = aptName;
	}
	public String getAptAddress() {
		return aptAddress;
	}
	public void setAptAddress(String aptAddress) {
		this.aptAddress = aptAddress;
	}
}
