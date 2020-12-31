package com.launchcode.sunrise_meditation_java.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.launchcode.sunrise_meditation_java.model.NewUser;
import com.launchcode.sunrise_meditation_java.model.User;
import com.launchcode.sunrise_meditation_java.service.UserService;

@RestController
public class UserController {
	
	Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(path = "/register")
	public String registerUser(@RequestBody NewUser newUser) {

		List<User> users = new ArrayList<>();
		users = userService.getAllUsers();
		boolean validUser = false;
		String statusMessage = null;
		User saveUser = new User();
		
		/* Lookup user in database by e-mail - Starts */
		if(newUser != null) {
			
			if(newUser.getUserName() != "" 
					&& newUser.getEmailId() != ""
					&& newUser.getPassword() != "" 
					&& newUser.getConfirmPassword() != "" 
					&& newUser.getWeeklyGoal() != "") {
				
				if(!newUser.getPassword().trim().equals(newUser.getConfirmPassword().trim())) {
					statusMessage = "Password and Confirm Password doesn't matches.";
					return statusMessage;
				}
				for (User user : users) {
					if (user != null && user.getEmailId().equals(newUser.getEmailId())) {
						statusMessage = "There is already a user registsered with this email. Pls enter another valid email Id.";
						return statusMessage;
					} else { validUser = true; } 
				}
				
			} else {
				statusMessage = "Please enter Valid details.";
				return statusMessage;
			}
		} else {
			statusMessage = "Please enter Valid details.";
			return statusMessage;
		}
		/* Lookup user in database by e-mail - Ends */

		/* If the User is valid : add the user */
		if (validUser) {
			saveUser.setUserName(newUser.getUserName());
			saveUser.setEmailId(newUser.getEmailId());
			saveUser.setPassword(newUser.getPassword());
			saveUser.setWeeklyGoal(Integer.valueOf(newUser.getWeeklyGoal()));
			
			userService.saveUser(saveUser);
			statusMessage = "User is successfully registered! Please login with your credentials";
		}
		log.info("statusMessage-{}",statusMessage);

		return statusMessage;
	}

}
