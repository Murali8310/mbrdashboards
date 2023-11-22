package com.titan.stationary.bean;

public class HolidayMasterBean {
	
	@Override
	public String toString() {
		return "HolidayMasterBean [Holiday_date=" + Holiday_date + ", Holiday_day=" + Holiday_day + ", Occasion="
				+ Occasion + ", Activestatus=" + Activestatus + ", Costcentre=" + Costcentre + ", Year=" + Year + "]";
	}
	private String Holiday_date;
	private String Holiday_day;
	private String Occasion;
	private String Activestatus;
	private String Costcentre;
	private String Year;
	public String getYear() {
		return Year;
	}
	public void setYear(String year) {
		Year = year;
	}
	public String getCostcentre() {
		return Costcentre;
	}
	public void setCostcentre(String costcentre) {
		Costcentre = costcentre;
	}
	public String getHoliday_date() {
		return Holiday_date;
	}
	public void setHoliday_date(String holiday_date) {
		Holiday_date = holiday_date;
	}
	public String getHoliday_day() {
		return Holiday_day;
	}
	public void setHoliday_day(String holiday_day) {
		Holiday_day = holiday_day;
	}
	public String getOccasion() {
		return Occasion;
	}
	public void setOccasion(String occasion) {
		Occasion = occasion;
	}
	public String getActivestatus() {
		return Activestatus;
	}
	public void setActivestatus(String activestatus) {
		Activestatus = activestatus;
	}
	
	
	

}
