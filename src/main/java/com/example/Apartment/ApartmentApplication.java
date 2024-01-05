package com.example.Apartment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ARUN VEMIREDDY
 *
 */

@SpringBootApplication(scanBasePackages = { "com.example" })
@EnableSwagger2
public class ApartmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApartmentApplication.class, args);
	}

}
