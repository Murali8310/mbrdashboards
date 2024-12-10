package com.titan.mbrDashboard.dto;

import java.math.BigDecimal;

public class OutputDashboardTiles {
	private Integer year;
	private Integer month;
	private BigDecimal orderValue;
	private Integer orderQuentity;
	private Integer delears;
	private Integer totalOrder;
	private String region;
	private Integer startDate;
	private String endDate;
	
	public Integer getYear() {
		return year;
	}
	public Integer getMonth() {
		return month;
	}
	public BigDecimal getOrderValue() {
		return orderValue;
	}
	public Integer getOrderQuentity() {
		return orderQuentity;
	}
	public Integer getDelears() {
		return delears;
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
	public void setOrderValue(BigDecimal orderValue) {
		this.orderValue = orderValue;
	}
	public void setOrderQuentity(Integer orderQuentity) {
		this.orderQuentity = orderQuentity;
	}
	public void setDelears(Integer delears) {
		this.delears = delears;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public Integer getTotalOrder() {
		return totalOrder;
	}
	public void setTotalOrder(Integer totalOrder) {
		this.totalOrder = totalOrder;
	}
	public Integer getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setStartDate(Integer startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	

}
