package com.titan.stationary.dto;

import java.math.BigDecimal;

public class OutputRegionWiseGrowthOverPreviousMonth {
	private Integer month;
	private Integer year;
	private BigDecimal totalRevenue;
	private Integer totalQTY;
	private Integer totalRetailerCode;
	private BigDecimal priceGrowthPercentage;
	private BigDecimal orderQtyGrowthPercentage;
	private BigDecimal retailerGrowthPercentage;
	private String region;
	private BigDecimal priceGrowth;
	private Integer orderGrowth;
	private Integer retailerGrowth;
	
	public Integer getMonth() {
		return month;
	}
	public Integer getYear() {
		return year;
	}
	public BigDecimal getTotalRevenue() {
		return totalRevenue;
	}
	public Integer getTotalQTY() {
		return totalQTY;
	}
	public Integer getTotalRetailerCode() {
		return totalRetailerCode;
	}
	public BigDecimal getPriceGrowthPercentage() {
		return priceGrowthPercentage;
	}
	public BigDecimal getOrderQtyGrowthPercentage() {
		return orderQtyGrowthPercentage;
	}
	public BigDecimal getRetailerGrowthPercentage() {
		return retailerGrowthPercentage;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public void setYear(Integer year) {
		this.year = year;
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
	public void setPriceGrowthPercentage(BigDecimal priceGrowthPercentage) {
		this.priceGrowthPercentage = priceGrowthPercentage;
	}
	public void setOrderQtyGrowthPercentage(BigDecimal orderQtyGrowthPercentage) {
		this.orderQtyGrowthPercentage = orderQtyGrowthPercentage;
	}
	public void setRetailerGrowthPercentage(BigDecimal retailerGrowthPercentage) {
		this.retailerGrowthPercentage = retailerGrowthPercentage;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public BigDecimal getPriceGrowth() {
		return priceGrowth;
	}
	public Integer getOrderGrowth() {
		return orderGrowth;
	}
	public Integer getRetailerGrowth() {
		return retailerGrowth;
	}
	public void setPriceGrowth(BigDecimal priceGrowth) {
		this.priceGrowth = priceGrowth;
	}
	public void setOrderGrowth(Integer orderGrowth) {
		this.orderGrowth = orderGrowth;
	}
	public void setRetailerGrowth(Integer retailerGrowth) {
		this.retailerGrowth = retailerGrowth;
	}

}
