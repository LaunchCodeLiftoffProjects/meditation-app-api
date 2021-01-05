package com.launchcode.sunrise_meditation_java.service;
import com.launchcode.sunrise_meditation_java.model.Meditation;
import com.launchcode.sunrise_meditation_java.repository.MeditationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
