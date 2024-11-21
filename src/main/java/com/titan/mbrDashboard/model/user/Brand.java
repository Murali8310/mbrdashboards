package com.titan.mbrDashboard.model.user;

import javax.persistence.Entity;
import javax.persistence.Table;

public class Brand {
	private int id;
	private String Brand;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBrand() {
		return Brand;
	}
	public void setBrand(String brand) {
		Brand = brand;
	}
	
	
	
}
