package com.titan.mbrDashboard.dto;

import java.math.BigDecimal;

public class OutputPercentageofOrdersbyDayoftheMonth {
private Integer year;
private Integer Month;
private Integer day;
private Integer distinctOrderCount;
private BigDecimal percentageOfOrders;

public Integer getYear() {
	return year;
}
public Integer getMonth() {
	return Month;
}
public Integer getDistinctOrderCount() {
	return distinctOrderCount;
}
public BigDecimal getPercentageOfOrders() {
	return percentageOfOrders;
}
public void setYear(Integer year) {
	this.year = year;
}
public void setMonth(Integer month) {
	Month = month;
}
public void setDistinctOrderCount(Integer distinctOrderCount) {
	this.distinctOrderCount = distinctOrderCount;
}
public void setPercentageOfOrders(BigDecimal percentageOfOrders) {
	this.percentageOfOrders = percentageOfOrders;
}
public Integer getDay() {
	return day;
}
public void setDay(Integer day) {
	this.day = day;
}

}
