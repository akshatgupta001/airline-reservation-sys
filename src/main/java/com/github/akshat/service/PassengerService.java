package com.github.akshat.service;

import com.github.akshat.entities.PassengerEntity;
import com.github.akshat.repository.PassengerRepository;

/**
 * @author akshatgupta
 * @Date 22/06/24
 */

public class PassengerService {

    PassengerRepository passengerRepository;
    public PassengerService(PassengerRepository passengerRepository){
        this.passengerRepository = passengerRepository;
    }
    public void addPassenger(String name, String email, String phoneNumber) {
        passengerRepository.addPassenger(name, email, phoneNumber);
    }
    public PassengerEntity getPassengerByName(String name) {
        return passengerRepository.getPassengerByName(name);
    }
}
