package com.github.akshat.service;

import com.github.akshat.entities.BookingEntity;
import com.github.akshat.entities.PassengerEntity;
import com.github.akshat.enums.BookingStatus;
import com.github.akshat.enums.SeatClassEnum;
import com.github.akshat.exceptions.DataNotFoundException;
import com.github.akshat.repository.BookingRepository;

import java.util.Date;
import java.util.List;

/**
 * @author akshatgupta
 * @Date 22/06/24
 */

public class BookingService {
    BookingRepository bookingRepository;
    FlightManagementService flightManagementService;
    PassengerService passengerService;

    FareService fareService;

    public BookingService(BookingRepository bookingRepository, FlightManagementService flightManagementService, PassengerService passengerService, FareService fareService) {
        this.bookingRepository = bookingRepository;
        this.flightManagementService = flightManagementService;
        this.passengerService = passengerService;
        this.fareService = fareService;
    }

    //make booking based on flight number, passenger id, seat class and number of seats
    public void bookFlight(String flightNumber, String passengerName, SeatClassEnum seatClass, int numSeats) {
        //get the fare for the seat class
        double fare = 0;
        try {
            fare = fareService.getFare(flightNumber, seatClass);
        } catch (DataNotFoundException e) {
            System.out.println("Flight not found. Aborting the booking");
            return;
        }
        //get the total fare
        double totalFare = fare * numSeats;
        // verify passenger
        PassengerEntity passenger = passengerService.getPassengerByName(passengerName);
        if (passenger == null) {
            System.out.println("Passenger not found. Aborting the booking");
            return;
        }
        //update the number of seats available
        try{
            flightManagementService.updateSeats(flightNumber, seatClass, numSeats);
        } catch (Exception e) {
            //if booking fails, abort the booking
            System.out.println("Booking failed. Aborting  the booking");
            return;
        }
        //add the booking to the repository
        bookingRepository.addBooking(createBookingEntity(flightNumber, passengerName, seatClass, numSeats, totalFare));
        //print the booking details
        System.out.println("Booking confirmed for passenger: " + passengerName + " for flight: " + flightNumber + " in class: " + seatClass + " for " + numSeats + " seats. Total fare: " + totalFare);
    }

    BookingEntity createBookingEntity(String flightNumber, String passengerName, SeatClassEnum seatClass, int numSeats, double totalFare) {
        Date currentDate = new Date();
        return new BookingEntity(flightNumber, passengerName, seatClass, numSeats, totalFare, currentDate, BookingStatus.CONFIRMED);
    }

    public void getBookingsByPassenger(String passengerName) {
        List<BookingEntity> bookings = bookingRepository.getBookingsByPassengerName(passengerName);
        if (bookings.isEmpty()) {
            System.out.println("No bookings found for passenger: " + passengerName);
            return;
        }
        // print all bookings
        for (BookingEntity booking : bookings) {
            System.out.println(booking.toString());
        }
    }

    //cancel all bookings for a flight
    public void cancelBookingsByFlight(String flightNumber) {
        bookingRepository.cancelBookingsByFlightId(flightNumber);
        System.out.println("All bookings for flight: " + flightNumber + " cancelled");
    }

}
