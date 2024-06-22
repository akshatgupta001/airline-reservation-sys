package com.github.akshat.entities;

import com.github.akshat.enums.SeatClassEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @author akshatgupta
 * @Date 22/06/24
 */


@Data
@Builder
public class FlightEntity {
    private String flightNumber;
    private String origin;
    private String destination;
    private String airline;
    private Schedule schedule;
    private Map<SeatClassEnum, Integer> availableSeats; // Map of class to available seats
    private Map<SeatClassEnum, Double> baseFare; // Map of class to base fare

    public FlightEntity(String flightNumber, String origin, String destination, String airline, Schedule schedule, Map<SeatClassEnum, Integer> availableSeats, Map<SeatClassEnum, Double> baseFare) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.airline = airline;
        this.schedule = schedule;
        this.availableSeats = availableSeats;
        this.baseFare = baseFare;
    }


    @Override
    public String toString() {
        // pretty print the flight details for the user
        return "FlightEntity{" +
                "flightNumber='" + flightNumber + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", airline='" + airline + '\'' +
                ", departureTime=" + schedule.getStartTime() +
                ", arrivalTime=" + schedule.getEndTime() +
                ", availableSeats=" + availableSeats +
                ", baseFare=" + baseFare +
                '}';
    }

    public Integer getAvailableSeats(String seatClass) {
        SeatClassEnum seatClassEnum = SeatClassEnum.valueOf(seatClass);
        return availableSeats.get(seatClassEnum);
    }
    public Double getFareBySeatClass(SeatClassEnum seatClassEnum) {
        return baseFare.get(seatClassEnum);
    }

    public void updateSeats(SeatClassEnum seatClass, Integer seats) {
        // throw error if seats are not available
        if (availableSeats.get(seatClass) < seats) {
            throw new IllegalArgumentException("Seats not available");
        }
        availableSeats.put(seatClass, availableSeats.get(seatClass) - seats);
    }

}

