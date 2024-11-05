package com.titan.stationary.dto;

import java.util.List;

import com.titan.stationary.model.user.*;

public class MasterData {
	private List<Region> region;
	private List<Brand> brand;
	private List<RSName>rsName;
//	private List<ABMName>abmName;
	
	
	public List<Region> getRegion() {
		return region;
	}
	public void setRegion(List<Region> region) {
		this.region = region;
	}
	public List<Brand> getBrand() {
		return brand;
	}
	public void setBrand(List<Brand> brand) {
		this.brand = brand;
	}
	public List<RSName> getRsName() {
		return rsName;
	}
	public void setRsName(List<RSName> rsName) {
		this.rsName = rsName;
	}
//	public List<ABMName> getAbmName() {
//		return abmName;
//	}
//	public void setAbmName(List<ABMName> abmName) {
//		this.abmName = abmName;
//	}
	
	
	
}
