package com.titan.mbrDashboard.dto;

public class OutputTopSKUOrderedOverall {
	private String productCode;
	private Integer totalOrderQty;
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
