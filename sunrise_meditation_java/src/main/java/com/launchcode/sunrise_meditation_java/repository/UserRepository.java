package com.launchcode.sunrise_meditation_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.launchcode.sunrise_meditation_java.model.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	//@Query("SELECT email_id FROM ")
	User findByEmailId(String emailId);

	User findByResetPasswordToken(String token);
}
