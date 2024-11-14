package com.titan.stationary.dto;

import java.math.BigDecimal;

public class OutputRegionWiseMonthlyAvgPerOrder {

	private Integer year;
	private Integer month;
	private Integer avgQtyPerOrder;
	private BigDecimal avgPricePerOrder;
	private String region;
	public Integer getYear() {
		return year;
	}
	public Integer getMonth() {
		return month;
	}
	public Integer getAvgQtyPerOrder() {
		return avgQtyPerOrder;
	}
	public BigDecimal getAvgPricePerOrder() {
		return avgPricePerOrder;
	}
	public String getRegion() {
		return region;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public void setAvgQtyPerOrder(Integer avgQtyPerOrder) {
		this.avgQtyPerOrder = avgQtyPerOrder;
	}
	public void setAvgPricePerOrder(BigDecimal avgPricePerOrder) {
		this.avgPricePerOrder = avgPricePerOrder;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	
	
	
}
