package com.titan.mbrDashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.titan.mbrDashboard")

public class MbrDashboard{

	public static void main(String[] args) {
		SpringApplication.run(MbrDashboard.class, args);
		
	}
}
