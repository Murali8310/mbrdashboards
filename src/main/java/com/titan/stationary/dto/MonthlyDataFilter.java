package com.titan.stationary.dto;

public class MonthlyDataFilter {
	private String regionList;
	private Integer startDate;
	private Integer endDate;
	private String brandList;
	private String rsNameList;
	private String abmName;
	private String retailerType;
	
	

	public String getRegionList() {
		return regionList;
	}
	public Integer getStartDate() {
		return startDate;
	}
	public Integer getEndDate() {
		return endDate;
	}
	public String getBrandList() {
		return brandList;
	}
	public String getRsNameList() {
		return rsNameList;
	}
	public void setRegionList(String regionList) {
		this.regionList = regionList;
	}
	public void setStartDate(Integer startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
	}
	public void setBrandList(String brandList) {
		this.brandList = brandList;
	}
	public void setRsNameList(String rsNameList) {
		this.rsNameList = rsNameList;
	}
	public String getAbmName() {
		return abmName;
	}
	public void setAbmName(String abmName) {
		this.abmName = abmName;
	}
	
	public String getRetailerType() {
		return retailerType;
	}
	public void setRetailerType(String retailerType) {
		this.retailerType = retailerType;
	}

}
