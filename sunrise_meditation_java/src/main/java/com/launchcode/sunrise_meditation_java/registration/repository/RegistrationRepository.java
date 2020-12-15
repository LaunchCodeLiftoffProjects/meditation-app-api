package com.launchcode.sunrise_meditation_java.registration.repository ;

import org.springframework.data.jpa.repository.JpaRepository;

import com.launchcode.sunrise_meditation_java.registration.model.User;

public interface RegistrationRepository extends JpaRepository<User, Long> {

}
