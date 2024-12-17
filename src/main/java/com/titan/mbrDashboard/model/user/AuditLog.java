package com.titan.mbrDashboard.model.user;

import java.time.LocalDateTime;

public class AuditLog {
	private String userName;
	private String Action;
	private String userID;
	
	private LocalDateTime actionDateTime;
	
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
	public LocalDateTime getActionDateTime() {
		return actionDateTime;
	}
	public void setActionDateTime(LocalDateTime actionDateTime) {
		this.actionDateTime = actionDateTime;
	}
	
	
}
