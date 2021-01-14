package com.launchcode.sunrise_meditation_java.model;

import java.util.Date;

public class UserDetails {

	private Long userId;
	private String userName;
	private String emailId;
	private int weeklyGoal;
	private double totalGoalAchieved;
	private Date memberSince;
	
	public UserDetails() {
		
	}

	public UserDetails(Long userId, String userName, String emailId, int weeklyGoal, double totalGoalAchieved, Date memberSince) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.emailId = emailId;
		this.weeklyGoal = weeklyGoal;
		this.totalGoalAchieved = totalGoalAchieved;
		this.memberSince = memberSince;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public int getWeeklyGoal() {
		return weeklyGoal;
	}

	public void setWeeklyGoal(int weeklyGoal) {
		this.weeklyGoal = weeklyGoal;
	}

	public double getTotalGoalAchieved() {
		return totalGoalAchieved;
	}

	public void setTotalGoalAchieved(double totalGoalAchieved) {
		this.totalGoalAchieved = totalGoalAchieved;
	}

	public Date getMemberSince() {
		return memberSince;
	}

	public void setMemberSince(Date memberSince) {
		this.memberSince = memberSince;
	}
	
	
}
