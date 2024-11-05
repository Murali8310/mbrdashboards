package com.titan.stationary.dto;

import java.math.BigDecimal;

public class OutputForMontlyFilter {
	
	private BigDecimal totalRevenue;
	private Integer totalQTY;
	private Integer totalRetailerCode;
	
	
	public BigDecimal getTotalRevenue() {
		return totalRevenue;
	}
	public Integer getTotalQTY() {
		return totalQTY;
	}
	public Integer getTotalRetailerCode() {
		return totalRetailerCode;
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
}
