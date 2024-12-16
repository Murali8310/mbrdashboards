package com.titan.mbrDashboard.dto;

public class MonthlyDataFilter {
	private String regionList;
	private Integer startDate;
	private Integer endDate;
	private String brandList;
	private String rsNameList;
	private String abmName;
	private String retailerType;
	private String selectedMonths;
	private String selectedYears;
	private String selectedState;
	private String selectedCity;
//	private String size;
	

//	public String getSize() {
//		return size;
//	}
//	public void setSize(String size) {
//		this.size = size;
//	}
	public String getSelectedState() {
		return selectedState;
	}
	public String getSelectedCity() {
		return selectedCity;
	}
	public void setSelectedState(String selectedState) {
		this.selectedState = selectedState;
	}
	public void setSelectedCity(String selectedCity) {
		this.selectedCity = selectedCity;
	}
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
	public String getSelectedMonths() {
		return selectedMonths;
	}
	public String getSelectedYears() {
		return selectedYears;
	}
	public void setSelectedMonths(String selectedMonths) {
		this.selectedMonths = selectedMonths;
	}
	public void setSelectedYears(String selectedYears) {
		this.selectedYears = selectedYears;
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
