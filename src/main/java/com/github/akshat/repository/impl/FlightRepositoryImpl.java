package com.github.akshat.repository.impl;

import com.github.akshat.exceptions.DataNotFoundException;
import com.github.akshat.entities.FlightEntity;
import com.github.akshat.entities.FlightSearchQuery;
import com.github.akshat.enums.SeatClassEnum;
import com.github.akshat.repository.FlightRepository;

import java.util.*;

/**
 * @author akshatgupta
 * @Date 22/06/24
 */

public class FlightRepositoryImpl implements FlightRepository {
    HashMap<String, FlightEntity> flightsMap = new HashMap<>();
    // map for origin to list of flight ID
    HashMap<String, List<String>> originToFlightMap = new HashMap<>();

    // map for start time to list of flight ID
    HashMap<Date, List<String>> startTimeToFlightMap = new HashMap<>();
    public FlightRepositoryImpl() {
        this.flightsMap = new HashMap<>();
        this.originToFlightMap = new HashMap<>();
    }
    @Override
    public List<FlightEntity> getAllFlights(FlightSearchQuery flightSearchQuery) {
        List<FlightEntity> matchingFlights = new ArrayList<>();
        Set<String> filteredFLightIds = new HashSet<>();
        // Filter flight IDs by origin

        if (flightSearchQuery.getOrigin() != null) {
            List<String> flightIds = originToFlightMap.get(flightSearchQuery.getOrigin());
            if (flightIds != null) {
                filteredFLightIds.addAll(flightIds);
            }
        }

        // Filter by start time
        if (flightSearchQuery.getStartTime() != null) {
            List<String> flightIds = startTimeToFlightMap.get(flightSearchQuery.getStartTime());
            if (flightIds != null) {
                filteredFLightIds.addAll(flightIds);
            }
        }

        // if filtered values are empty then get list of all flights
        if (filteredFLightIds.isEmpty()) {
            filteredFLightIds.addAll(flightsMap.keySet());
        }
        // set matching flights satisfying all criteria
        for (String flightId : filteredFLightIds) {
            FlightEntity flight = flightsMap.get(flightId);
            if (matchesSearchCriteria(flight, flightSearchQuery)) {
                matchingFlights.add(flight);
            }
        }
        return matchingFlights;
    }

    @Override
    public FlightEntity getFlightByNumber(String flightNumber) {
        return flightsMap.get(flightNumber);
    }

    @Override
    public void addFlight(FlightEntity flight) {
        flightsMap.put(flight.getFlightNumber(), flight);
        updateOriginToFlightMap(flight);
        updateStartTimeToFlightMap(flight);
    }

    //add to originToFlightMap
    private void updateOriginToFlightMap(FlightEntity flight) {
        if (originToFlightMap.containsKey(flight.getOrigin())) {
            originToFlightMap.get(flight.getOrigin()).add(flight.getFlightNumber());
        } else {
            List<String> flightList = new ArrayList<>();
            flightList.add(flight.getFlightNumber());
            originToFlightMap.put(flight.getOrigin(), flightList);
        }
    }

    // remove from originToFlightMap
    private void removeFromOriginToFlightMap(FlightEntity flight) {
        originToFlightMap.get(flight.getOrigin()).remove(flight.getFlightNumber());
    }

    private void updateStartTimeToFlightMap(FlightEntity flight) {
        Date date = flight.getSchedule().getStartTime();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        if (startTimeToFlightMap.containsKey(date)) {
            startTimeToFlightMap.get(date).add(flight.getFlightNumber());
        } else {
            List<String> flightList = new ArrayList<>();
            flightList.add(flight.getFlightNumber());
            startTimeToFlightMap.put(date, flightList);
        }
    }

    // remove from startTimeToFlightMap
    private void removeFromStartTimeToFlightMap(FlightEntity flight) {
        Date date = flight.getSchedule().getStartTime();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        startTimeToFlightMap.get(date).remove(flight.getFlightNumber());
    }

    @Override
    public void updateFlight(FlightEntity flight) {
        // check if flight exists
        if (!flightsMap.containsKey(flight.getFlightNumber())) {
            throw new IllegalArgumentException("Flight does not exist"); // TODO custom exception
        }
        //check if origin changed
        if (!flightsMap.get(flight.getFlightNumber()).getOrigin().equals(flight.getOrigin())) {
            // remove from old origin
            removeFromOriginToFlightMap(flightsMap.get(flight.getFlightNumber()));
            // add to new origin
            updateOriginToFlightMap(flight);
        }
        // check if start time changed
        if (!flightsMap.get(flight.getFlightNumber()).getSchedule().getStartTime().equals(flight.getSchedule().getStartTime())) {
            // remove from old start time
            removeFromStartTimeToFlightMap(flightsMap.get(flight.getFlightNumber()));
            // add to new start time
            updateStartTimeToFlightMap(flight);
        }
        flightsMap.put(flight.getFlightNumber(), flight);

    }

    @Override
    public void cancelFlight(String flightNumber) {
        if (!flightsMap.containsKey(flightNumber)) {
            throw new IllegalArgumentException("Flight does not exist"); // TODO custom exception
        }
        removeFromOriginToFlightMap(flightsMap.get(flightNumber));
        removeFromStartTimeToFlightMap(flightsMap.get(flightNumber));
        flightsMap.remove(flightNumber);

    }

    @Override
    public int getAvailableSeats(String flightNumber, String seatClass) {
        return flightsMap.get(flightNumber).getAvailableSeats(seatClass);
    }

    @Override
    public void updateSeats(String flightNumber, SeatClassEnum seatClass, Integer seatBooked) throws DataNotFoundException {
        if(!flightsMap.containsKey(flightNumber)) {
            throw new DataNotFoundException("Flight does not exist");
        }

        flightsMap.get(flightNumber).updateSeats(seatClass, seatBooked);
    }

    @Override
    public double getFare(String flightNumber, SeatClassEnum seatClass) throws DataNotFoundException {
        // check if flight exists
        if (!flightsMap.containsKey(flightNumber)) {
            throw new DataNotFoundException("Flight does not exist");
        }
        return flightsMap.get(flightNumber).getFareBySeatClass(seatClass);
    }

    private boolean matchesSearchCriteria(FlightEntity flight, FlightSearchQuery flightSearchQuery) {
        if (flightSearchQuery.getOrigin() != null && !flightSearchQuery.getOrigin().equals(flight.getOrigin())) {
            return false;
        }
        if (flightSearchQuery.getDestination() != null && !flightSearchQuery.getDestination().equals(flight.getDestination())) {
            return false;
        }
        // check if start time is same day
        if (flightSearchQuery.getStartTime() != null && !isSameDay(flightSearchQuery.getStartTime(), flight.getSchedule().getStartTime())) {
            return false;
        }
        // check if end time is same day
        if (flightSearchQuery.getStartTime() != null && !isSameDay(flightSearchQuery.getEndTime(), flight.getSchedule().getEndTime())) {
            return false;
        }
        if (flightSearchQuery.getAirline() != null && !flightSearchQuery.getAirline().equals(flight.getAirline())) {
            return false;
        }
        // check for seat availability
        if (flightSearchQuery.getSeatClass() != null && flightSearchQuery.getSeats() != null) {
            if (flight.getAvailableSeats(flightSearchQuery.getSeatClass()) < flightSearchQuery.getSeats()) {
                return false;
            }
        }

        return true;
    }

    private Date getDateWithoutTime(Date date) {
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return date;
    }
    private boolean isSameDay(Date date1, Date date2) {
        return getDateWithoutTime(date1).equals(getDateWithoutTime(date2));
    }
}
