package com.titan.stationary.dto;

import java.math.BigDecimal;

public class OutputDashboardGraphs {
	private Integer year;
	private Integer month;
	private BigDecimal orderValue;
	private Integer totalOrder;
	public Integer getYear() {
		return year;
	}
	public Integer getMonth() {
		return month;
	}
	public BigDecimal getOrderValue() {
		return orderValue;
	}
	public Integer getTotalOrder() {
		return totalOrder;
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
	public void setTotalOrder(Integer totalOrder) {
		this.totalOrder = totalOrder;
	}
	
	
	
	

}
