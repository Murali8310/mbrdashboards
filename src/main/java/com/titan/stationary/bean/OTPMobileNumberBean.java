package com.titan.stationary.bean;

import org.springframework.stereotype.Component;

@Component
public class OTPMobileNumberBean {

	private String mobileNumber;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Override
	public String toString() {
		return "OTPMobileNumberBean [mobileNumber=" + mobileNumber + "]";
	}

	
	
}
