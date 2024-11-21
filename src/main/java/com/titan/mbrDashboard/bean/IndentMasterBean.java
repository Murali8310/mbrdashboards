package com.titan.mbrDashboard.bean;

public class IndentMasterBean {
	
	private String EmpCode;

	private String Password;

	private String EmpName;

	private String LMSID;
	private String STORECODE;

	private String Email;
	
	private String MobileNumber;

	@Override
	public String toString() {
		return "IndentMasterBean [EmpCode=" + EmpCode + ", Password=" + Password + ", EmpName=" + EmpName + ", LMSID="
				+ LMSID + ", STORECODE=" + STORECODE + ", Email=" + Email + ", MobileNumber=" + MobileNumber + "]";
	}

	public String getEmpCode() {
		return EmpCode;
	}

	public void setEmpCode(String empCode) {
		EmpCode = empCode;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getEmpName() {
		return EmpName;
	}

	public void setEmpName(String empName) {
		EmpName = empName;
	}

	public String getLMSID() {
		return LMSID;
	}

	public void setLMSID(String lMSID) {
		LMSID = lMSID;
	}

	public String getSTORECODE() {
		return STORECODE;
	}

	public void setSTORECODE(String sTORECODE) {
		STORECODE = sTORECODE;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getMobileNumber() {
		return MobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		MobileNumber = mobileNumber;
	}

	
	

}
