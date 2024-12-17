package com.titan.mbrDashboard.dto;

import java.math.BigDecimal;

public class OutputTopSKUOrderedOverall {
	private String productCode;
	private Integer totalOrderQty;
	private BigDecimal totalPrice;
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
	

}
