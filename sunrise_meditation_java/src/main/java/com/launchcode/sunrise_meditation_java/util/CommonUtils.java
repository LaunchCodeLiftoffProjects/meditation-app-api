package com.launchcode.sunrise_meditation_java.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.launchcode.sunrise_meditation_java.model.User;
import com.launchcode.sunrise_meditation_java.service.UserService;

@Component
public class CommonUtils {

	@Autowired
	private UserService userService;
	
	public boolean isLoggedInUserPresent(String username) {
		
		boolean validUser = false;
		
		User user = getUserDetailsByEmail(username);
		if(user != null && user.getEmailId() != null) {
			if(username.equalsIgnoreCase(user.getEmailId())) {
				validUser = true;
			}
				
		}
		
		return validUser;
	}
	
	public User getUserDetailsByEmail(String emailId) {
		return userService.getUserDetailsByEmail(emailId);
	}
	
	public boolean isLoggedInUserValid(String username, String password) {
		
		boolean userValid = false;
		
		User user = getUserDetailsByEmail(username);
		if(user != null) {
			if(username.equalsIgnoreCase(user.getEmailId())
					&& password.equalsIgnoreCase(user.getPassword())) {
				userValid = true;
			}
				
		}
		
		return userValid;
	}
	
	public Long getUserIdByEmail(String emailId) {
		User user = getUserDetailsByEmail(emailId);
		return user.getUserId();
	}
}
