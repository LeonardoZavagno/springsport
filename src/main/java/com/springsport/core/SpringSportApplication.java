package com.springsport.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.springsport.core")
public class SpringSportApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSportApplication.class, args);

		//docker container --> docker run --name postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=testPsw -e POSTGRES_DB=postgres -p 5432:5432 -d postgres
		//maven --> ./mvnw.cmd clean package
		//index --> http://localhost:8080/ leonardo
		//users --> http://localhost:8080/api/v1/users
	}

}