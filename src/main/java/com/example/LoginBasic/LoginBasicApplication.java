package com.example.LoginBasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:8080")
@EnableScheduling
public class LoginBasicApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(LoginBasicApplication.class, args);
		
	}
}