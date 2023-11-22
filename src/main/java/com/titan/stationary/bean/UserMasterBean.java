package com.titan.stationary.bean;

public class UserMasterBean {

	private String userName;

	private String companyName;

	private String email;

	private String password;

	private String loginId;

	private String userType;

	private String userGroup;

	private String bussinessType;

	private String location;

	private String region;

	private String state;

	private String storeCode;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserMasterBean [userName=");
		builder.append(userName);
		builder.append(", companyName=");
		builder.append(companyName);
		builder.append(", email=");
		builder.append(email);
		builder.append(", password=");
		builder.append(password);
		builder.append(", loginId=");
		builder.append(loginId);
		builder.append(", userType=");
		builder.append(userType);
		builder.append(", userGroup=");
		builder.append(userGroup);
		builder.append(", bussinessType=");
		builder.append(bussinessType);
		builder.append(", location=");
		builder.append(location);
		builder.append(", region=");
		builder.append(region);
		builder.append(", state=");
		builder.append(state);
		builder.append(", storeCode=");
		builder.append(storeCode);
		builder.append("]");
		return builder.toString();
	}

}
