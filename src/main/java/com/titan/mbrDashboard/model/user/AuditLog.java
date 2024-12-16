package com.titan.mbrDashboard.model.user;

public class AuditLog {
	private String userName;
	private String Action;
	private String userID;
	
	public String getUserName() {
		return userName;
	}
	public String getAction() {
		return Action;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setAction(String action) {
		Action = action;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	
}
