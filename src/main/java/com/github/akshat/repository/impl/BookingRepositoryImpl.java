package com.github.akshat.repository.impl;

import com.github.akshat.entities.BookingEntity;
import com.github.akshat.enums.BookingStatus;
import com.github.akshat.repository.BookingRepository;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author akshatgupta
 * @Date 22/06/24
 */

@Data

public class BookingRepositoryImpl implements BookingRepository {


    private Map<String, BookingEntity> bookingMap; // Mapping bookingId to BookingEntity
    private Map<String, List<String>> passengerToBookingMap; // Mapping passengerName to list of bookingIds
    private Map<String, List<String>> flightToBookingMap; // Mapping flightNumber to list of bookingIds

    public BookingRepositoryImpl() {
        this.bookingMap = new HashMap<>();
        this.passengerToBookingMap = new HashMap<>();
        this.flightToBookingMap = new HashMap<>();
    }


    @Override
    public BookingEntity addBooking(BookingEntity bookingEntity) {
        bookingMap.put(bookingEntity.getBookingId(), bookingEntity);
        List<String> passengerBookings = passengerToBookingMap.get(bookingEntity.getPassengerName());
        if (passengerBookings == null) {
            passengerBookings = new ArrayList<>();
        }
        passengerBookings.add(bookingEntity.getBookingId());
        passengerToBookingMap.put(bookingEntity.getPassengerName(), passengerBookings);

        List<String> flightBookings = flightToBookingMap.get(bookingEntity.getFlightNumber());
        if (flightBookings == null) {
            flightBookings = new ArrayList<>();
        }
        flightBookings.add(bookingEntity.getBookingId());
        flightToBookingMap.put(bookingEntity.getFlightNumber(), flightBookings);
        return bookingEntity;
    }

    @Override
    public List<BookingEntity> getBookingsByPassengerName(String passengerName) {
        List<BookingEntity> bookings = new ArrayList<>();
        List<String> bookingIds = passengerToBookingMap.get(passengerName);
        if (bookingIds == null) {
            return bookings;
        }
        for (String bookingId : bookingIds) {
            bookings.add(bookingMap.get(bookingId));
        }
        return bookings;
    }

    @Override
    public void cancelBookingsByFlightId(String flightId) {
        List<String> bookingIds = flightToBookingMap.get(flightId);
        if (bookingIds == null) {
            return;
        }
        for (String bookingId : bookingIds) {
            // print the booking details
            bookingMap.get(bookingId).setBookingStatus(BookingStatus.CANCELLED);
        }
    }

}

