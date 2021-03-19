package com.example.common;

public enum ResponseStatus {
	
	SUCCESS("SUCCESS"),ERROR("ERROR");
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private ResponseStatus(String status) {
		this.status = status;
	}
}
