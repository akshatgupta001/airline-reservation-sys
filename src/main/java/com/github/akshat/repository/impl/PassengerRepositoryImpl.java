package com.github.akshat.repository.impl;

import com.github.akshat.entities.PassengerEntity;
import com.github.akshat.repository.PassengerRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akshatgupta
 * @Date 22/06/24
 */

public class PassengerRepositoryImpl implements PassengerRepository {
    Map<String,PassengerEntity> passengers ;

    public PassengerRepositoryImpl(){
        this.passengers = new HashMap<>();
    }
    @Override
    public void addPassenger(String name, String email, String phoneNumber) {
        PassengerEntity passengerEntity = new PassengerEntity(name, email, phoneNumber);
        passengers.put(passengerEntity.getName(), passengerEntity);
        System.out.println("Passenger added: " + passengerEntity);
    }

    @Override
    public PassengerEntity getPassengerByName(String name) {
        PassengerEntity passengerEntity = passengers.get(name);
        System.out.println("Passenger fetched: " + name);
        return passengerEntity;
    }

}
