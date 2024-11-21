package com.titan.mbrDashboard.bean;

public class BuyerMasterBean {
	private String EmpCode;

	private String EmpName;

	private String STORECODE;

	private String email;

	@Override
	public String toString() {
		return "BuyerMasterBean [EmpCode=" + EmpCode + ", EmpName=" + EmpName + ", STORECODE=" + STORECODE + ", email="
				+ email + ", MobileNumber=" + MobileNumber + "]";
	}

	public String getEmpCode() {
		return EmpCode;
	}

	public void setEmpCode(String empCode) {
		EmpCode = empCode;
	}

	public String getEmpName() {
		return EmpName;
	}

	public void setEmpName(String empName) {
		EmpName = empName;
	}

	public String getSTORECODE() {
		return STORECODE;
	}

	public void setSTORECODE(String sTORECODE) {
		STORECODE = sTORECODE;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return MobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		MobileNumber = mobileNumber;
	}

	private String MobileNumber;

}
