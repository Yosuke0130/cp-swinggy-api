package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GolfAppApplication {

	@Autowired
	static Logging logger;

	public static void main(String[] args) {
		SpringApplication.run(GolfAppApplication.class, args);
		logger.info("Let's play GOLF!!");
	}

}
