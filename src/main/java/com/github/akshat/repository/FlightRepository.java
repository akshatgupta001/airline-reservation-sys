package com.github.akshat.repository;

import com.github.akshat.entities.FlightEntity;
import com.github.akshat.entities.FlightSearchQuery;
import com.github.akshat.enums.SeatClassEnum;

import java.util.List;

public interface FlightRepository {
    List<FlightEntity> getAllFlights(FlightSearchQuery flightSearchQuery);
    FlightEntity getFlightByNumber(String flightNumber);
    void addFlight(FlightEntity flight);
    void updateFlight(FlightEntity flight);
    void cancelFlight(String flightNumber);
    int getAvailableSeats(String flightNumber, String seatClass);

    void updateSeats(String flightNumber, SeatClassEnum seatClass, Integer seatBooked);

    double getFare(String flightNumber, SeatClassEnum seatClass);
}
