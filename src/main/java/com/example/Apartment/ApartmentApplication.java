package com.example.Apartment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author ARUN VEMIREDDY
 *
 */
@SpringBootApplication(scanBasePackages={
"com.example.security.services",
"com.example.common",
"com.example.Apartment.Util",
"com.example.Apartment.Entity",
"com.example.Apartment.Controller",
"com.example.Apartment.Dao",
"com.example.Apartment.DTO",
"com.example.Apartment.Service",
"com.example.Apartment.ServiceImpl",
"com.example.security.filter"})
public class ApartmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApartmentApplication.class, args);
	}

}
