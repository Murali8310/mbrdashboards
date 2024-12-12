package com.titan.mbrDashboard.controller;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Task {
	  private final UserController userController;

	    @Autowired
	    public Task(UserController userController) {
	        this.userController = userController;
	    }
	    
	@Scheduled(initialDelay = 1000, fixedRate = 24 * 60 * 60 * 1000)
	  public void run() {  
	//	this.userController.get7thworkingDay();
		 //  System.out.println("this.userController.get7thworkingDay() ::: " + this.userController.get7thworkingDay());
	   //System.out.println("Current time is ::: " + LocalDateTime.now());
	   //this.userController.sevenDayMailTrigger();
	  }
	

}