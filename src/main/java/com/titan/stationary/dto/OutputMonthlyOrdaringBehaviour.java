package com.titan.stationary.dto;

import java.math.BigDecimal;

public class OutputMonthlyOrdaringBehaviour {
	private Integer year;
	private Integer month;
	private Integer noOforders;
	private BigDecimal avgQtyPerOrder;
	private BigDecimal avgValuePerOrder;
	public Integer getYear() {
		return year;
	}
	public Integer getMonth() {
		return month;
	}
	public Integer getNoOforders() {
		return noOforders;
	}
	public BigDecimal getAvgQtyPerOrder() {
		return avgQtyPerOrder;
	}
	public BigDecimal getAvgValuePerOrder() {
		return avgValuePerOrder;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public void setNoOforders(Integer noOforders) {
		this.noOforders = noOforders;
	}
	public void setAvgQtyPerOrder(BigDecimal avgQtyPerOrder) {
		this.avgQtyPerOrder = avgQtyPerOrder;
	}
	public void setAvgValuePerOrder(BigDecimal avgValuePerOrder) {
		this.avgValuePerOrder = avgValuePerOrder;
	}
	

}
