package com.launchcode.sunrise_meditation_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.launchcode.sunrise_meditation_java.model.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmailId(String emailId);

	User findByResetPasswordToken(String token);
}
