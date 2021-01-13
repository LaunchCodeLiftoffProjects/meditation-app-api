package com.launchcode.sunrise_meditation_java.repository;

import com.launchcode.sunrise_meditation_java.model.Meditation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MeditationRepository extends JpaRepository<Meditation, Long> {
}
