package com.example.Apartment.DTO;

public class ApartmentDetailsDTO {

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
