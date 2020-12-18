package com.launchcode.sunrise_meditation_java.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.launchcode.sunrise_meditation_java.model.User;
import com.launchcode.sunrise_meditation_java.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(path = "/register")
	public String registerUser(@RequestBody User newUser) {

		List<User> users = new ArrayList<>();
		users = userService.getAllUsers();
		boolean isUserExists = false;
		String statusMessage = null;

		/* Lookup user in database by e-mail - Starts */
		if (newUser.getEmailId() != null) {
			for (User user : users) {
				if (user != null && user.getEmailId().equals(newUser.getEmailId())) {
					isUserExists = true;
					statusMessage = "There is already a user registsered with this email. Pls enter another valid email Id.";
					System.out.println("\"OOPS! There is already a user registered with the email");

				}
			}
		}
		/* Lookup user in database by e-mail - Ends */

		/* If User doesn't exist : add the user */
		if (!isUserExists) {
			userService.saveUser(newUser);
			statusMessage = "User is successfully registered! Please login with your credentials";
		}

		return statusMessage;
	}

}
