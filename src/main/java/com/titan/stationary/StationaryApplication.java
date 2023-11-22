package com.titan.stationary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.titan.stationary")

public class StationaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(StationaryApplication.class, args);
		
	}

}
