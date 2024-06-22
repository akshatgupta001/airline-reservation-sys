package com.github.akshat.repository;

import com.github.akshat.entities.PassengerEntity;

public interface PassengerRepository {
    void addPassenger(String name, String email, String phoneNumber);
    PassengerEntity getPassengerByName(String name);

}
