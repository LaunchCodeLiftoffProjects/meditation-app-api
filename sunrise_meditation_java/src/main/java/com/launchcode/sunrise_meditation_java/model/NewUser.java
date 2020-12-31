package com.launchcode.sunrise_meditation_java.model;

public class NewUser {

	private String userName;
	private String emailId;
	private String password;
	private String confirmPassword;
	private String weeklyGoal;	

	public NewUser(String userName, String emailId, String password, String confirmPassword, String weeklyGoal) {
		super();		
		this.userName = userName;
		this.emailId = emailId;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.weeklyGoal = weeklyGoal;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getWeeklyGoal() {
		return weeklyGoal;
	}

	public void setWeeklyGoal(String weeklyGoal) {
		this.weeklyGoal = weeklyGoal;
	}
	
	
}
