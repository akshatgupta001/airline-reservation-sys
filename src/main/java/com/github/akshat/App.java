package com.github.akshat;

import com.github.akshat.entities.FlightEntity;
import com.github.akshat.enums.Command;
import com.github.akshat.enums.SeatClassEnum;
import com.github.akshat.repository.impl.BookingRepositoryImpl;
import com.github.akshat.repository.impl.FlightRepositoryImpl;
import com.github.akshat.repository.impl.PassengerRepositoryImpl;
import com.github.akshat.service.BookingService;
import com.github.akshat.service.FareService;
import com.github.akshat.service.FlightManagementService;
import com.github.akshat.service.PassengerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Welcome To Airline System" );


       //initialise services
        FlightManagementService flightManagementService = new FlightManagementService(new FlightRepositoryImpl());
        PassengerService passengerService = new PassengerService(new PassengerRepositoryImpl());
        FareService fareService = new FareService(flightManagementService);
        BookingService bookingService = new BookingService(new BookingRepositoryImpl(), flightManagementService, passengerService, fareService);
        //initialise some flight

        addFlights(flightManagementService);


        while(true) {
            // get user input
            // get command
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter Command: ");
            // supported commands
            System.out.print("Supported Commands: [");
            // print commands
            for (Command command : Command.values()) {
                System.out.print(command.toString() + " ");
            }
            System.out.println( "]");
            // list all commands

            String command = scan.nextLine();
            Command commandEnum = Command.get(command);
            if(commandEnum == null) {
                System.out.println("Invalid Command");
                continue;
            }
            switch (commandEnum) {
                case ADD_FLIGHT:
                    // take input in a single line comma separated like : "AI202", "DEL", "BOM", "Air India", "2022-06-24 10:00:00", "2022-06-24 12:00:00"
                    System.out.println("Enter Flight Details : [flightNumber, origin, destination, airline, departureTime, arrivalTime]");
                    String flightDetails = scan.nextLine();
                    String[] flightDetailsArray = flightDetails.split(",");
                    String flightNumber = flightDetailsArray[0].trim();
                    String origin = flightDetailsArray[1].trim();
                    String destination = flightDetailsArray[2].trim();
                    String airline = flightDetailsArray[3].trim();
                    String departureTime = flightDetailsArray[4].trim();
                    String arrivalTime = flightDetailsArray[5].trim();
                    Map<SeatClassEnum, Integer> availableSeats = getDefaultAvailableSeats();
                    Map<SeatClassEnum, Double> baseFare = getDefaultBaseFare();
                    flightManagementService.addFlight(flightNumber, origin, destination, airline, departureTime, arrivalTime, availableSeats, baseFare);
                    break;
                case ADD_PASSENGER:
                    // take input in a single line comma separated like : "John Doe", "
                    System.out.println("Enter Passenger Details: [name, email, phone]");
                    String passengerDetails = scan.nextLine();
                    String[] passengerDetailsArray = passengerDetails.split(",");
                    String name = passengerDetailsArray[0].trim();
                    String email = passengerDetailsArray[1].trim();
                    String phone = passengerDetailsArray[2].trim();
                    passengerService.addPassenger(name, email, phone);
                    break;
                case SEARCH_FLIGHT:
                    // take input in a single line comma separated like : "DEL", "BOM", null, null, null,null,null
                    System.out.println("Enter Search Criteria: [origin, destination, startTime, endTime, airline,seatClass,seats]");
                    //press Enter to search all flight
                    System.out.println("Press Enter to search all flights");
                    String searchCriteria = scan.nextLine();
                    String[] searchCriteriaArray = searchCriteria.split(",");
                    if(searchCriteriaArray.length <= 1) {
                        List<FlightEntity> flightEntityList = flightManagementService.getAllFlights(null,null,null,null,null,null,null);

                        for (FlightEntity flightEntity : flightEntityList) {
                            System.out.println(flightEntity.toString());
                        }
                    }else {
                        String originSearch = searchCriteriaArray[0].trim();
                        String destinationSearch = searchCriteriaArray[1].trim();
                        String startTime = searchCriteriaArray[2].trim();
                        String endTime = searchCriteriaArray[3].trim();
                        String airlineSearch = searchCriteriaArray[4].trim();
                        String seatClass = searchCriteriaArray[5].trim();
                        String seatsString = searchCriteriaArray[6].trim();
                        Integer seats = seatsString.equals("null") ? null : Integer.valueOf(seatsString);
                        List<FlightEntity> flightEntityList = flightManagementService.getAllFlights(originSearch, destinationSearch, startTime, endTime, airlineSearch, seatClass, seats);
                        if(flightEntityList.isEmpty()) {
                            System.out.println("No Flights Found");
                        }
                        for (FlightEntity flightEntity : flightEntityList) {
                            System.out.println(flightEntity.toString());
                        }
                    }
                    break;
                case BOOK_FLIGHT:
                    // take input in a single line comma separated like : "AI202", "John Doe", "ECONOMY", 1
                    System.out.println("Enter Booking Details: [flightNumber, passengerName, seatClass, seats]");
                    String bookingDetails = scan.nextLine();
                    String[] bookingDetailsArray = bookingDetails.split(",");
                    String flightNumberBooking = bookingDetailsArray[0].trim();
                    String passengerName = bookingDetailsArray[1].trim();
                    SeatClassEnum seatClassEnum = SeatClassEnum.valueOf(bookingDetailsArray[2].trim());
                    Integer seatsBooked = Integer.valueOf(bookingDetailsArray[3].trim());
                    bookingService.bookFlight(flightNumberBooking, passengerName, seatClassEnum, seatsBooked);
                    break;
                case GET_BOOKINGS:
                    // take input in a single line comma separated like : "John Doe"
                    System.out.println("Enter Passenger Name: ");
                    String nameOfBooking = scan.nextLine();
                    bookingService.getBookingsByPassenger(nameOfBooking);
                    break;
                case CANCEL_FLIGHT:
                    // take input in a single line comma separated like : "AI202"
                    System.out.println("Enter Flight Number to Cancel: ");
                    String flightNumberToCancel = scan.nextLine();
                    flightManagementService.cancelFlight(flightNumberToCancel);
                    bookingService.cancelBookingsByFlight(flightNumberToCancel);
                    break;
                case EXIT:
                    return;
                default:
                    System.out.println("Invalid Command");
            }
        }

    }

    public static void sampleInput() {
        // add_flight
        // AI602, DEL, BOM, Air India, 2022-06-24 10:00:00, 2022-06-24 12:00:00
        // add_passenger
        // akshat,akshat@gmail.com,786756453
        // search_flight
        // DEL,BOM,null,null, null,null,null
        // book_flight
        // AI203,akshat,PREMIUM,20
        // search_flight
        // DEL,BOM,null,null, null,PREMIUM,23
        // get_bookings
        // akshat
        // cancel_flight
        // AI203
        // get_bookings
        // akshat
        // exit
    }

    private static Map<SeatClassEnum,Integer> getDefaultAvailableSeats() {
        Map<SeatClassEnum,Integer> availableSeats = new HashMap<>();
        availableSeats.put(SeatClassEnum.ECONOMY, 100);
        availableSeats.put(SeatClassEnum.BUSINESS, 50);
        availableSeats.put(SeatClassEnum.PREMIUM, 20);
        return availableSeats;
    }

    // set for base fares
    private static Map<SeatClassEnum,Double> getDefaultBaseFare() {
        Map<SeatClassEnum,Double> baseFare = new HashMap<>();
        baseFare.put(SeatClassEnum.ECONOMY, 100.0);
        baseFare.put(SeatClassEnum.BUSINESS, 200.0);
        baseFare.put(SeatClassEnum.PREMIUM, 300.0);
        return baseFare;
    }
    static void addFlights(FlightManagementService flightManagementService) {
        flightManagementService.addFlight("AI202", "DEL", "BOM", "Air India", "2022-06-24 10:00:00", "2022-06-24 12:00:00", getDefaultAvailableSeats(), getDefaultBaseFare());
        flightManagementService.addFlight("AI203", "DEL", "BOM", "Air India", "2022-06-24 14:00:00", "2022-06-24 16:00:00", getDefaultAvailableSeats(), getDefaultBaseFare());
        flightManagementService.addFlight("IG204", "DEL", "BLR", "Indigo", "2022-06-24 18:00:00", "2022-06-24 20:00:00", getDefaultAvailableSeats(), getDefaultBaseFare());
        flightManagementService.addFlight("IG205", "DEL", "BLR", "Indigo", "2022-07-24 22:00:00", "2022-08-25 00:00:00", getDefaultAvailableSeats(), getDefaultBaseFare());
    }
}
