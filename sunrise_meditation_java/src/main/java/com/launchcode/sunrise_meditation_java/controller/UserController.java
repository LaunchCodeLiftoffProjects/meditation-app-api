package com.launchcode.sunrise_meditation_java.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.launchcode.sunrise_meditation_java.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.launchcode.sunrise_meditation_java.model.Meditation;
import com.launchcode.sunrise_meditation_java.service.MeditationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.launchcode.sunrise_meditation_java.model.NewUser;
import com.launchcode.sunrise_meditation_java.model.User;
import com.launchcode.sunrise_meditation_java.model.UserDetails;
import com.launchcode.sunrise_meditation_java.service.UserService;
import com.launchcode.sunrise_meditation_java.util.CommonUtils;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {
	
	Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private MeditationService meditationService;
	
	@Autowired
	private CommonUtils commonUtils;
	private Long id;
	private User weeklyGoal;

	@GetMapping("/loginSuccess/{loggedUser}")
	public Long loginSuccess(@PathVariable("loggedUser") String loggedUser) {
		log.info("******************* LOGIN SUCCESS ************************");
		return commonUtils.getUserIdByEmail(loggedUser);
	}
	
	@PostMapping(path = "/register")
	public String registerUser(@RequestBody NewUser newUser) {

		List<User> users = new ArrayList<>();
		users = userService.getAllUsers();
		boolean validUser = false;
		String statusMessage = null;
		User saveUser = new User();
		log.info("*************** REGISTRATION PROCESS *******************");
		/* Lookup user in database by e-mail - Starts */
		if (newUser != null) {

			if (newUser.getUserName() != "" && newUser.getEmailId() != "" && newUser.getPassword() != ""
					&& newUser.getConfirmPassword() != "" && newUser.getWeeklyGoal() != "") {

				if (!newUser.getPassword().trim().equals(newUser.getConfirmPassword().trim())) {
					statusMessage = "Password and Confirm Password doesn't matches.";
					return statusMessage;
				}

				if (users != null && users.size() > 0) {
					for (User user : users) {
						if (user != null && user.getEmailId().equals(newUser.getEmailId())) {
							statusMessage = "There is already a user registsered with this email. Pls enter another valid email Id.";
							return statusMessage;
						} else {
							validUser = true;
						}
					}
				} else {
					validUser = true;
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
			saveUser.setCreatedTimestamp(new Date(System.currentTimeMillis()));
			
			userService.saveUser(saveUser);
			statusMessage = "User is successfully registered! Please login with your credentials";
		}
		log.info("statusMessage-{}",statusMessage);

		return statusMessage;
	}

	@PostMapping(path = "/meditation")
	public Meditation saveMeditation(@RequestBody Meditation meditation){
		log.info("*************** SAVE MEDITATION *******************");
		System.out.println("meditation java" + meditation);
		return meditationService.saveMeditation(meditation);

	}

	@GetMapping(path = "/userProfile/{id}")
	@ResponseBody
	public UserDetails retrieveUserDetails(@PathVariable("id") Long id) {

		log.info("*************** RETRIEVE USER DETAILS *******************");
		
		List<Meditation> meditationLogs = new ArrayList<>();
		User user = userService.getUserDetailsById(id);
		UserDetails userDetails = new UserDetails();
		double totalGoalAchieved = 0.00d;
		meditationLogs
		 = meditationService.getMeditationLogsById(id);
		
		if(user != null) {
			userDetails.setUserId(user.getUserId());
			userDetails.setEmailId(user.getEmailId());
			userDetails.setUserName(user.getUserName());
			userDetails.setWeeklyGoal(user.getWeeklyGoal());
			userDetails.setMemberSince(user.getCreatedTimestamp());
			
			if (meditationLogs != null && meditationLogs.size() > 0) {
				
				for (Meditation meditation : meditationLogs) {
					totalGoalAchieved = totalGoalAchieved + Double.valueOf(meditation.getTime_log().trim());				
				}
				log.info("totalGoalAchieved : " + totalGoalAchieved);
			}
			userDetails.setTotalGoalAchieved(totalGoalAchieved);
			log.info("userDetails : " + userDetails.toString());
		}
		
		
		return userDetails;
	}

	@PutMapping("/updateProfile/{id}")
	public String updateWeeklyGoal(@PathVariable("id") Long id,@RequestBody User updateUser)
	{
		log.info("*************** UPDATE USER DETAILS *******************");
		User user = userService.getUserDetailsById(id);
		String statusMessage = null;
		if(updateUser.getWeeklyGoal() != null) {
			user.setWeeklyGoal(Integer.valueOf(updateUser.getWeeklyGoal()));
			userService.saveUser(user);
			System.out.println(user.getWeeklyGoal());
			statusMessage = "Update Success";
		} else {
			statusMessage = "Please enter valid value";
		}

		return statusMessage;
	}


}
