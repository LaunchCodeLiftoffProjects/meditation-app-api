package com.launchcode.sunrise_meditation_java.controller;

import com.launchcode.sunrise_meditation_java.model.User;
import com.launchcode.sunrise_meditation_java.repository.UserRepository;
import com.launchcode.sunrise_meditation_java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserRepository repository;


	@Autowired
	private UserService userService;


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam(value = "username", defaultValue = "null") String username,
						HttpServletRequest request,
						HttpServletRequest response,
						HttpSession session)
	{
		if((request.getAttribute("IsValidRequest")!= null) && request.getAttribute("IsValidRequest").equals("false")){
			return "Invalid Request! Token Mismatch Error!";
		}
		String headerPassword = request.getHeader("password").trim();

		if(session.isNew()){
			session.setMaxInactiveInterval(100);
			for (User user : repository.findAll()) {
				if (user.getUserName().equals(username.trim())) {
					if (bCryptPasswordEncoder.matches(headerPassword,user.getPassword())) {
						System.out.println("Valid Credentials");
						if (!user.isLoggedIn()) {
							user.setLoggedIn(true);
							repository.save(user);
							session.setAttribute("token","randomtoken"+username);
							return "User Logged In Successfully!";
						} else {
							return "User Already Logged In";
						}
					} else
						return "Password Incorrect Error!!";
				}
			}
			return "User Name Not Found";
		}

		return "User Already Logged In";
	}
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(@RequestParam(value = "username",defaultValue = "null")String username, HttpSession session,
						 HttpServletRequest request){
		if(session.isNew()){
			session.invalidate();
			return "User Not Logged In!!";
		}
		else{
			for(User user: repository.findAll()){
				if(user.getUserName().equals(username)){
					if(user.isLoggedIn()) {
						user.setLoggedIn(false);
						repository.save(user);
					}
					else{
						return "User Not Logged In";
					}

				}
			}
			session.invalidate();
			return "User Logged out Successfully";
		}
	}


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
