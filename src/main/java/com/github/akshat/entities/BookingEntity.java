package com.github.akshat.entities;

import com.github.akshat.enums.BookingStatus;
import com.github.akshat.enums.SeatClassEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author akshatgupta
 * @Date 22/06/24
 */

@Data
public class BookingEntity {

    private static int count = 0;

    private String bookingId;
    private String flightNumber;
    private String passengerName;
    private SeatClassEnum seatClass;
    private int numSeats;
    private double totalFare;
    private Date bookingDate;
    private BookingStatus bookingStatus;

    public BookingEntity(String flightNumber, String passengerName, SeatClassEnum seatClass, int numSeats, double totalFare, Date bookingDate, BookingStatus bookingStatus) {
        this.bookingId = "B" + ++count;
        this.flightNumber = flightNumber;
        this.passengerName = passengerName;
        this.seatClass = seatClass;
        this.numSeats = numSeats;
        this.totalFare = totalFare;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
    }

    public String toString() {
        return "BookingEntity{" +
                "bookingId='" + bookingId + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", seatClass=" + seatClass +
                ", numSeats=" + numSeats +
                ", totalFare=" + totalFare +
                ", bookingDate=" + bookingDate +
                ", bookingStatus='" + bookingStatus + '\'' +
                '}';
    }
}
