package com.github.akshat.service;

import com.github.akshat.enums.SeatClassEnum;
import com.github.akshat.exceptions.DataNotFoundException;

/**
 * @author akshatgupta
 * @Date 22/06/24
 */

public class FareService {
    FlightManagementService flightManagementService;
    public FareService(FlightManagementService flightManagementService) {
        this.flightManagementService = flightManagementService;
    }
    public double getFare(String flightNumber, SeatClassEnum seatClass) throws DataNotFoundException {
        // get the fare for the seat class
        return flightManagementService.getFare(flightNumber, seatClass);
    }
}
