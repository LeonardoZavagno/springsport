package com.springsport.judo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication()
public class JudoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(JudoApplication.class, args);

		//maven --> ./mvnw.cmd clean package
		//index --> http://localhost:8080/judo/index.html
		//users --> http://localhost:8080/judo/api/v1/users
	}

}
