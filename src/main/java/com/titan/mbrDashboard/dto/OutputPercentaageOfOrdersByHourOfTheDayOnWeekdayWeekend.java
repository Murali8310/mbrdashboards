package com.titan.mbrDashboard.dto;

import java.math.BigDecimal;

public class OutputPercentaageOfOrdersByHourOfTheDayOnWeekdayWeekend {

	private Integer month;
	private String dayType;
	private Integer orderHour;
	private Integer distinctOrderCount;
	private BigDecimal percentageOfOrders;
	public Integer getMonth() {
		return month;
	}
	public String getDayType() {
		return dayType;
	}
	public Integer getOrderHour() {
		return orderHour;
	}
	public Integer getDistinctOrderCount() {
		return distinctOrderCount;
	}
	public BigDecimal getPercentageOfOrders() {
		return percentageOfOrders;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public void setDayType(String dayType) {
		this.dayType = dayType;
	}
	public void setOrderHour(Integer orderHour) {
		this.orderHour = orderHour;
	}
	public void setDistinctOrderCount(Integer distinctOrderCount) {
		this.distinctOrderCount = distinctOrderCount;
	}
	public void setPercentageOfOrders(BigDecimal percentageOfOrders) {
		this.percentageOfOrders = percentageOfOrders;
	}
	
	
}
