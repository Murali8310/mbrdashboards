package com.titan.stationary.dto;

import java.math.BigDecimal;

public class OutputRegionWiseMonthlyDistributionNoofOrders {

	private Integer year;
	private Integer Month;
	private String region;
	private Integer noOfOrders;
	private Integer noOfOrdersPercentage;
	
	public Integer getYear() {
		return year;
	}
	public Integer getMonth() {
		return Month;
	}
	public String getRegion() {
		return region;
	}
	public Integer getNoOfOrders() {
		return noOfOrders;
	}
	public Integer getNoOfOrdersPercentage() {
		return noOfOrdersPercentage;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public void setMonth(Integer month) {
		Month = month;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public void setNoOfOrders(Integer noOfOrders) {
		this.noOfOrders = noOfOrders;
	}
	public void setNoOfOrdersPercentage(Integer noOfOrdersPercentage) {
		this.noOfOrdersPercentage = noOfOrdersPercentage;
	}
	
}
