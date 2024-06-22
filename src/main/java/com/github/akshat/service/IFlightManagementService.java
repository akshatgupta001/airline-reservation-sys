package com.github.akshat.service;

import com.github.akshat.entities.FlightEntity;
import com.github.akshat.enums.SeatClassEnum;

import java.util.List;
import java.util.Map;

public interface IFlightManagementService {
    public List<FlightEntity> getAllFlights(String origin, String destination, String startTimeString, String endTimeString, String airline, String seatClass, Integer seats) ;
    public FlightEntity getFlightByNumber(String flightNumber);
    public void addFlight(String flightNumber, String origin, String destination, String airline, String startTimeString, String endTimeString, Map<SeatClassEnum, Integer> availableSeats, Map<SeatClassEnum, Double> baseFare) ;
    public void cancelFlight(String flightNumber);
}
