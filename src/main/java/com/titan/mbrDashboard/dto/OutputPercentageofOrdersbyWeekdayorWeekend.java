package com.titan.mbrDashboard.dto;

import java.math.BigDecimal;

public class OutputPercentageofOrdersbyWeekdayorWeekend {

	private Integer month;
	private String dayType;
	private Integer distinctOrderCount;
	private BigDecimal percentageOfOrders;
	
	public String getDayType() {
		return dayType;
	}
	public Integer getDistinctOrderCount() {
		return distinctOrderCount;
	}
	public BigDecimal getPercentageOfOrders() {
		return percentageOfOrders;
	}
	public void setDayType(String dayType) {
		this.dayType = dayType;
	}
	public void setDistinctOrderCount(Integer distinctOrderCount) {
		this.distinctOrderCount = distinctOrderCount;
	}
	public void setPercentageOfOrders(BigDecimal percentageOfOrders) {
		this.percentageOfOrders = percentageOfOrders;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	
	
}
