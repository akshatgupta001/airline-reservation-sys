package com.github.akshat.repository;

import com.github.akshat.entities.BookingEntity;
import com.github.akshat.entities.FlightEntity;
import com.github.akshat.enums.SeatClassEnum;

import java.util.Date;
import java.util.List;

public interface BookingRepository {
    BookingEntity addBooking(BookingEntity bookingEntity);
    List<BookingEntity> getBookingsByPassengerName(String passengerName);
    void cancelBookingsByFlightId(String flightId);
}
