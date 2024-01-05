package com.example.Apartment.DTO;

import org.springframework.web.multipart.MultipartFile;

public class OwnerDetailsDTO {

	private int id;

	private String name;

	private int flatno;

	private Long contact;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFlatno() {
		return flatno;
	}

	public void setFlatno(int flatno) {
		this.flatno = flatno;
	}

	public Long getContact() {
		return contact;
	}

	public void setContact(Long contact) {
		this.contact = contact;
	}
}
