package com.titan.mbrDashboard.dto;

import java.math.BigDecimal;

public class OutputPercentageofOrdersByWeekdayorWeekendRegionWise {
	private String region;
	private Integer month;
	private String dayType;
	private Integer distinctOrderCount;
	private BigDecimal percentageOfOrders;
	
	public String getRegion() {
		return region;
	}
	public Integer getMonth() {
		return month;
	}
	public String getDayType() {
		return dayType;
	}
	public Integer getDistinctOrderCount() {
		return distinctOrderCount;
	}
	public BigDecimal getPercentageOfOrders() {
		return percentageOfOrders;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public void setMonth(Integer month) {
		this.month = month;
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
}
