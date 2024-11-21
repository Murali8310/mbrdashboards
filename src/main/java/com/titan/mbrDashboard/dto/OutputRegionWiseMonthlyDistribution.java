package com.titan.mbrDashboard.dto;

import java.math.BigDecimal;

public class OutputRegionWiseMonthlyDistribution {

	
	private BigDecimal totalRevenue;
	private Integer totalQTY;
	private Integer totalRetailerCode;
	private Integer month;
	private Integer year;
	private String Region;
	
	
	public BigDecimal getTotalRevenue() {
		return totalRevenue;
	}
	public Integer getTotalQTY() {
		return totalQTY;
	}
	public Integer getTotalRetailerCode() {
		return totalRetailerCode;
	}
	public Integer getMonth() {
		return month;
	}
	public Integer getYear() {
		return year;
	}
	public String getRegion() {
		return Region;
	}
	public void setTotalRevenue(BigDecimal totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	public void setTotalQTY(Integer totalQTY) {
		this.totalQTY = totalQTY;
	}
	public void setTotalRetailerCode(Integer totalRetailerCode) {
		this.totalRetailerCode = totalRetailerCode;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public void setRegion(String region) {
		Region = region;
	}
	
}
