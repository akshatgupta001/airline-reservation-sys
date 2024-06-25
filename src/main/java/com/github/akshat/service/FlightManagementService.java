package com.github.akshat.service;

import com.github.akshat.exceptions.DataNotFoundException;
import com.github.akshat.entities.FlightEntity;
import com.github.akshat.entities.FlightSearchQuery;
import com.github.akshat.entities.Schedule;
import com.github.akshat.enums.SeatClassEnum;
import com.github.akshat.repository.FlightRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author akshatgupta
 * @Date 22/06/24
 */

public class FlightManagementService implements IFlightManagementService{
    private FlightRepository flightRepository;

    public FlightManagementService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    //update seats
    public void updateSeats(String flightNumber, SeatClassEnum seatClass, Integer seats) {
        try {
            flightRepository.updateSeats(flightNumber, seatClass, seats);
        } catch (DataNotFoundException e) {
            System.out.println("Flight not found");
        } catch (Exception e) {
            System.out.println("Booking failed. Aborting  the booking");
        }
    }
    public List<FlightEntity> getAllFlights(String origin, String destination, String startTimeString, String endTimeString, String airline,String seatClass, Integer seats) {

        // check and set null if String value is "null"
        if(origin!=null && origin.equals("null")) {
            origin = null;
        }
        if(destination!=null &&destination.equals("null")) {
            destination = null;
        }
        if(airline != null && airline.equals("null")) {
            airline = null;
        }


        // resolve startTime and endTime into Date
        Date startTime = getDateFromString(startTimeString);
        Date endTime = getDateFromString(endTimeString);
        // check if seatClass is null
        if(seatClass!=null && seatClass.equals("null")) {
            seatClass = null;
        }
        FlightSearchQuery flightSearchQuery = FlightSearchQuery.builder()
                .origin(origin)
                .destination(destination)
                .startTime(startTime)
                .endTime(endTime)
                .airline(airline)
                .seatClass(seatClass)
                .seats(seats)
                .build();
        return flightRepository.getAllFlights(flightSearchQuery);
    }

    public FlightEntity getFlightByNumber(String flightNumber) {
        return flightRepository.getFlightByNumber(flightNumber);
    }

    public void addFlight(String flightNumber, String origin, String destination, String airline, String startTimeString, String endTimeString, Map<SeatClassEnum, Integer> availableSeats, Map<SeatClassEnum, Double> baseFare) {
        // TODO : add validations on flight
        // check if flightNumber is unique
        if (flightRepository.getFlightByNumber(flightNumber) != null) {
            // TODO custom exception
            System.out.println("Flight number already exists");
            return;
        }
        Date startTime = getDateFromString(startTimeString);
        Date endTime = getDateFromString(endTimeString);
        FlightEntity flight = FlightEntity.builder()
                .flightNumber(flightNumber)
                .origin(origin)
                .destination(destination)
                .airline(airline)
                .schedule(new Schedule(startTime,endTime))
                .availableSeats(availableSeats)
                .baseFare(baseFare)
                .build();
        flightRepository.addFlight(flight);
        // print
        System.out.println("Flight added: " + flight.toString());
    }

    public void updateFlight(FlightEntity flight) {
        flightRepository.updateFlight(flight);
    }

    public void cancelFlight(String flightNumber) {
        flightRepository.cancelFlight(flightNumber);
    }

    public int getAvailableSeats(String flightNumber, String seatClass) {
        return flightRepository.getAvailableSeats(flightNumber, seatClass);
    }

    //getFare
    public double getFare(String flightNumber, SeatClassEnum seatClass) throws DataNotFoundException {

        return flightRepository.getFare(flightNumber, seatClass);
    }
    private Date getDateFromString(String startTimeString) {
        // convert date string to Date
        if( startTimeString==null ||startTimeString.equals("null")) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // adjust this pattern to match your time string
        Date startTime = null;
        try {
            startTime = formatter.parse(startTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startTime;
    }
    // get default values for
    // private Map<SeatClassEnum, Integer> availableSeats; // Map of class to available seats
    //    private Map<SeatClassEnum, Double> baseFare

    private Map<SeatClassEnum,Integer> getDefaultAvailableSeats() {
        Map<SeatClassEnum,Integer> availableSeats = new HashMap<>();
        availableSeats.put(SeatClassEnum.ECONOMY, 100);
        availableSeats.put(SeatClassEnum.BUSINESS, 50);
        availableSeats.put(SeatClassEnum.PREMIUM, 20);
        return availableSeats;
    }

    // set for base fares
    private Map<SeatClassEnum,Double> getDefaultBaseFare() {
        Map<SeatClassEnum,Double> baseFare = new HashMap<>();
        baseFare.put(SeatClassEnum.ECONOMY, 100.0);
        baseFare.put(SeatClassEnum.BUSINESS, 200.0);
        baseFare.put(SeatClassEnum.PREMIUM, 300.0);
        return baseFare;
    }


}
