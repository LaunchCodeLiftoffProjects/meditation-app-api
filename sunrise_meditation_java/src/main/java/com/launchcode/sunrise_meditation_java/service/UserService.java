package com.launchcode.sunrise_meditation_java.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

	public void resetPasswordToken(String token, String email) {
		User user = userRepository.findByEmailId(email);
		if (user != null){
			user.setResetPasswordToken(token);
			userRepository.save(user);
		} else{
			throw new UsernameNotFoundException( "Email: " + email + System.lineSeparator()
					+ "There is no user with that email :(" );
		}
	}

	public User get(String resetPasswordToken){
		return userRepository.findByResetPasswordToken(resetPasswordToken);
	}

	public void updatePassword(User user, String newPassword){
		/*If our project uses encryption

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		user.setPassword(encodedPassword);
		user.setResetPasswordToken(null); */
		user.setPassword(newPassword);
		user.setResetPasswordToken(null);

		userRepository.save(user);
	}

}
