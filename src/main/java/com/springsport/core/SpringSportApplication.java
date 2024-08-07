package com.springsport.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.springsport.core")
public class SpringSportApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSportApplication.class, args);

		//maven --> ./mvnw.cmd clean package
		//index --> http://localhost:8080/ leonardo
		//users --> http://localhost:8080/api/v1/users
	}

}