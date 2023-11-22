package com.titan.stationary.bean;

import java.io.Serializable;

public class UserLoginBean implements Serializable {

	private static final long serialVersionUID = -1999788775121701165L;

	private int user_id;

	private String user_Name;

	private String user_selection;//role

	private String company_Name;

	private String email_id;

	private int status;

	private String login_id;

	private String password;

	private int login_status;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_Name() {
		return user_Name;
	}

	public void setUser_Name(String user_Name) {
		this.user_Name = user_Name;
	}

	public String getCompany_Name() {
		return company_Name;
	}

	public void setCompany_Name(String company_Name) {
		this.company_Name = company_Name;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLogin_status() {
		return login_status;
	}

	public void setLogin_status(int login_status) {
		this.login_status = login_status;
	}

	public String getUser_selection() {
		return user_selection;
	}

	public void setUser_selection(String user_selection) {
		this.user_selection = user_selection;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserLoginBean [user_id=");
		builder.append(user_id);
		builder.append(", user_Name=");
		builder.append(user_Name);
		builder.append(", company_Name=");
		builder.append(company_Name);
		builder.append(", email_id=");
		builder.append(email_id);
		builder.append(", status=");
		builder.append(status);
		builder.append(", login_id=");
		builder.append(login_id);
		builder.append(", password=");
		builder.append(password);
		builder.append(", login_status=");
		builder.append(login_status);
		builder.append(", user_selection=");
		builder.append(user_selection);
		builder.append("]");
		return builder.toString();
	}

}
