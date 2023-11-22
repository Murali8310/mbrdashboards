package com.titan.stationary.bean;

public class ValidateOtpBean {

	private String mobileNumber;
	
	private int otpnum;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getOtpnum() {
		return otpnum;
	}

	public void setOtpnum(int otpnum) {
		this.otpnum = otpnum;
	}

	@Override
	public String toString() {
		return "ValidateOtpBean [mobileNumber=" + mobileNumber + ", otpnum=" + otpnum + "]";
	}	
}
