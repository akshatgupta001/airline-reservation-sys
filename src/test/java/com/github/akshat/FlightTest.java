package com.github.akshat;

import com.github.akshat.entities.FlightEntity;
import com.github.akshat.enums.SeatClassEnum;
import com.github.akshat.repository.impl.FlightRepositoryImpl;
import com.github.akshat.service.FlightManagementService;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author akshatgupta
 * @Date 22/06/24
 */

public class FlightTest extends TestCase {


    /**
     * Test method for getting a flight by its number.
     */
    public void testGetFlightByNumber() {
        System.out.println("TestGetFlightByNumber");
        // Create a new FlightManagementService object
        FlightManagementService flightManagementService = new FlightManagementService(new FlightRepositoryImpl());

        // Add flights
        App.addFlights(flightManagementService);

        // Get a flight by its number
        FlightEntity flight = flightManagementService.getFlightByNumber("AI202");
        System.out.println(flight.toString());

        // Verify that the flight was found correctly
        assertNotNull(flight);
    }

    // getAllFLights
    /**
     * Test method for getting all flights.
     */
    public void testGetAllFlights() {
        System.out.println("TestGetAllFlights");
        // Create a new FlightManagementService object
        FlightManagementService flightManagementService = new FlightManagementService(new FlightRepositoryImpl());

        // Add flights
        App.addFlights(flightManagementService);

        // Get all flights
        List<FlightEntity> flightEntityList = flightManagementService.getAllFlights(null, null, null, null, null,null,null);
        for (FlightEntity flight : flightEntityList) {
            System.out.println(flight.toString());
        }
        // Verify that the flights were found correctly
        assertEquals(4, flightEntityList.size());
    }
    /**
     * Test method for getting all flights.
     */
    public void testGetAllFlightsBySourceDest() {
        System.out.println("TestGetAllFlightsBySourceDest");
        // Create a new FlightManagementService object
        FlightManagementService flightManagementService = new FlightManagementService(new FlightRepositoryImpl());

        // Add flights
        App.addFlights(flightManagementService);

        // Get all flights
        List<FlightEntity> flightEntityList = flightManagementService.getAllFlights("DEL", "BOM", null, null, null,null,null);
        for(FlightEntity flight : flightEntityList) {
            System.out.println(flight.toString());
        }
        // Verify that the flights were found correctly
        assertEquals(2, flightEntityList.size());
    }


}
