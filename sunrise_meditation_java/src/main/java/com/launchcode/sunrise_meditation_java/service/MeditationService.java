package com.launchcode.sunrise_meditation_java.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.launchcode.sunrise_meditation_java.model.Meditation;
import com.launchcode.sunrise_meditation_java.repository.MeditationRepository;

@Service
public class MeditationService {
    private MeditationRepository meditationRepository;

    public MeditationService(MeditationRepository meditationRepository) {
        this.meditationRepository = meditationRepository;
    }

    public List<Meditation> getAll(){

        return meditationRepository.findAll();
    }

   public Meditation saveMeditation(Meditation meditation){

        return meditationRepository.save(meditation);
   }
   
   public List<Meditation> getMeditationLogsById(Long id) {

		List<Meditation> logs = new ArrayList<>();
		logs = meditationRepository.findByUserId(id);
		return logs;
   }
}
