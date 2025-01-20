package com.titan.mbrDashboard.dto;

import java.math.BigDecimal;

public class OutputTopSKUOrderedOverall {
	private String productCode;
	private Integer totalOrderQty;
	private BigDecimal totalPrice;
	private String  RetailerCode;
	private String RSName;
	private String Region;
	private String ABMType;
	
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getProductCode() {
		return productCode;
	}
	public Integer getTotalOrderQty() {
		return totalOrderQty;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public void setTotalOrderQty(Integer totalOrderQty) {
		this.totalOrderQty = totalOrderQty;
	}
	public String getRetailerCode() {
		return RetailerCode;
	}
	public String getRSName() {
		return RSName;
	}
	public String getRegion() {
		return Region;
	}
	public void setRetailerCode(String retailerCode) {
		RetailerCode = retailerCode;
	}
	public void setRSName(String rSName) {
		RSName = rSName;
	}
	public void setRegion(String region) {
		Region = region;
	}
	public String getABMType() {
		return ABMType;
	}
	public void setABMType(String aBMType) {
		ABMType = aBMType;
	}

	

}
