package com.launchcode.sunrise_meditation_java.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.launchcode.sunrise_meditation_java.model.User;
import com.launchcode.sunrise_meditation_java.repository.UserRepository;

@Service("userService")
public class UserService {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}

	public List<User> getAllUsers() {
		List<User> user = new ArrayList<>();
		userRepository.findAll().forEach(user::add);
		return user;
	}
	
	public User getUserDetailsById(Long id) {
		return userRepository.findById(id).get();
	}
	
	public User getUserDetailsByEmail(String emailId) {
		return userRepository.findByEmailId(emailId);
	}

}
