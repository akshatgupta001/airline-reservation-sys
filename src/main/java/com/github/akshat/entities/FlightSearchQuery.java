package com.github.akshat.entities;

import com.github.akshat.enums.SeatClassEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author akshatgupta
 * @Date 22/06/24
 */
@Data
@Builder
public class FlightSearchQuery {
    private String origin;
    private String destination;
    private Date startTime;
    private Date endTime;
    private String airline;
    private Integer seats;
    private String seatClass;

    public FlightSearchQuery(String origin, String destination, Date startTime, Date endTime, String airline,Integer seats,String seatClass) {
        this.origin = origin;
        this.destination = destination;
        this.startTime = startTime;
        this.endTime = endTime;
        this.airline = airline;
        this.seats = seats;
        this.seatClass = seatClass;
    }
}
