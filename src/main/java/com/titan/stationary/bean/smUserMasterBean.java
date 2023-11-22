package com.titan.stationary.bean;

public class smUserMasterBean {

	


	@Override
	public String toString() {
		return "smUserMasterBean [EmpCode=" + EmpCode + ", lmsid=" + lmsid + ", EmpName=" + EmpName + ", password="
				+ password + ", Companyemployee=" + Companyemployee + ", storeCode=" + storeCode + ", MobileNumber="
				+ MobileNumber + ", DateOfJoin=" + DateOfJoin + ", DateOfLeaving=" + DateOfLeaving + ", IsActive="
				+ IsActive + ", Email=" + Email + ", ABM=" + ABM + ", region=" + region + "]";
	}

	private String EmpCode;

	private String lmsid;

	private String EmpName;

	private String password;

	private String Companyemployee;

	private String storeCode;

	private String MobileNumber;

	private String DateOfJoin;

	private String DateOfLeaving;

	private String IsActive;
	
	private String Email;
	
	private String ABM;
	
	private String region;

	public String getEmpCode() {
		return EmpCode;
	}

	public void setEmpCode(String empCode) {
		EmpCode = empCode;
	}

	public String getLmsid() {
		return lmsid;
	}

	public void setLmsid(String lmsid) {
		this.lmsid = lmsid;
	}

	public String getEmpName() {
		return EmpName;
	}

	public void setEmpName(String empName) {
		EmpName = empName;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getMobileNumber() {
		return MobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		MobileNumber = mobileNumber;
	}

	public String getDateOfJoin() {
		return DateOfJoin;
	}

	public void setDateOfJoin(String dateOfJoin) {
		DateOfJoin = dateOfJoin;
	}

	public String getDateOfLeaving() {
		return DateOfLeaving;
	}

	public void setDateOfLeaving(String dateOfLeaving) {
		DateOfLeaving = dateOfLeaving;
	}

	public String getIsActive() {
		return IsActive;
	}

	public void setIsActive(String isActive) {
		IsActive = isActive;
	}

	public String getCompanyemployee() {
		return Companyemployee;
	}

	public void setCompanyemployee(String companyemployee) {
		Companyemployee = companyemployee;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getABM() {
		return ABM;
	}

	public void setABM(String aBM) {
		ABM = aBM;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}
