package com.launchcode.sunrise_meditation_java.repository;

import com.launchcode.sunrise_meditation_java.model.Meditation;
import com.launchcode.sunrise_meditation_java.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeditationRepository extends JpaRepository<Meditation, Long> {
}
