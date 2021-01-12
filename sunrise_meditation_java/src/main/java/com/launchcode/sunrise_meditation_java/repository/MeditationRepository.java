package com.launchcode.sunrise_meditation_java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.launchcode.sunrise_meditation_java.model.Meditation;

public interface MeditationRepository extends JpaRepository<Meditation, Long> {
	List<Meditation> findByUserId(Long id);
}
