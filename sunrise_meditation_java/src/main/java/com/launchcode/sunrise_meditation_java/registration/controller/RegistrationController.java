package com.launchcode.sunrise_meditation_java.registration.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.launchcode.sunrise_meditation_java.registration.model.User;
import com.launchcode.sunrise_meditation_java.registration.repository.RegistrationRepository;

@RestController
public class RegistrationController {

	@Autowired
	RegistrationRepository userRepository;

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "/create")
	public List<User> getAllUsers() {
		List<User> user = new ArrayList<>();
		userRepository.findAll().forEach(user::add);
		return user;
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/register")
    public User addUser(@RequestBody User user) {		
		userRepository.save(user);
        return user;
    }

}
