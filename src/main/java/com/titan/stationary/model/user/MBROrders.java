package com.titan.stationary.model.user;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MBROrders {
	@Id
    
	private String RetailerCode;
	private String RetailerName;
	private String OrderNo;
	private String ProductCode;
	private String Brand;
	private String Cluster;
	private  Integer OrderQty;
	private Integer TotalPrice;
	private String OrderTime;
	private String orderday;
	private String OrderDate;
	private String City;
	private String State;
	private String Region;;
	private String RSSOCode;
	private String RSCode;
	private String RSName;
	private String ABMEMM;
	private String ABMKAM;
	private String RBM;
	private String NSM;
	public String getRetailerCode() {
		return RetailerCode;
	}
	public void setRetailerCode(String retailerCode) {
		RetailerCode = retailerCode;
	}
	public String getRetailerName() {
		return RetailerName;
	}
	public void setRetailerName(String retailerName) {
		RetailerName = retailerName;
	}
	public String getOrderNo() {
		return OrderNo;
	}
	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}
	public String getProductCode() {
		return ProductCode;
	}
	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}
	public String getBrand() {
		return Brand;
	}
	public void setBrand(String brand) {
		Brand = brand;
	}
	public String getCluster() {
		return Cluster;
	}
	public void setCluster(String cluster) {
		Cluster = cluster;
	}
	public Integer getOrderQty() {
		return OrderQty;
	}
	public void setOrderQty(Integer orderQty) {
		OrderQty = orderQty;
	}
	public Integer getTotalPrice() {
		return TotalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		TotalPrice = totalPrice;
	}
	public String getOrderTime() {
		return OrderTime;
	}
	public void setOrderTime(String orderTime) {
		OrderTime = orderTime;
	}
	public String getOrderday() {
		return orderday;
	}
	public void setOrderday(String orderday) {
		this.orderday = orderday;
	}
	public String getOrderDate() {
		return OrderDate;
	}
	public void setOrderDate(String orderDate) {
		OrderDate = orderDate;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getRegion() {
		return Region;
	}
	public void setRegion(String region) {
		Region = region;
	}
	public String getRSSOCode() {
		return RSSOCode;
	}
	public void setRSSOCode(String rSSOCode) {
		RSSOCode = rSSOCode;
	}
	public String getRSCode() {
		return RSCode;
	}
	public void setRSCode(String rSCode) {
		RSCode = rSCode;
	}
	public String getRSName() {
		return RSName;
	}
	public void setRSName(String rSName) {
		RSName = rSName;
	}
	public String getABMEMM() {
		return ABMEMM;
	}
	public void setABMEMM(String aBMEMM) {
		ABMEMM = aBMEMM;
	}
	public String getABMKAM() {
		return ABMKAM;
	}
	public void setABMKAM(String aBMKAM) {
		ABMKAM = aBMKAM;
	}
	public String getRBM() {
		return RBM;
	}
	public void setRBM(String rBM) {
		RBM = rBM;
	}
	public String getNSM() {
		return NSM;
	}
	public void setNSM(String nSM) {
		NSM = nSM;
	}

}
