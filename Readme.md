Airline Reservation System CLI App
----------------------------------

Overview
--------

This is a command-line interface (CLI) app for managing airline reservations. It allows users to add flights, passengers, and bookings, as well as search for flights and cancel bookings.

Features
--------

-   Add Flights: Users can add new flights with details such as flight number, origin, destination, airline, departure time, and arrival time.

-   Add Passengers: Users can add new passengers with details such as name, email, and phone number.

-   Search Flights: Users can search for flights by origin, destination, departure time, arrival time, airline, seat class, and number of seats.

-   Book Flights: Users can book flights by specifying the flight number, passenger name, seat class, and number of seats.

-   Get Bookings: Users can view their bookings by specifying their name.

-   Cancel Flights: Users can cancel flights by specifying the flight number.

-   Exit: Users can exit the app by typing "exit".

Usage
-----

1.  Run the app by executing the App class.

2.  Enter the desired command (e.g., "add_flight", "add_passenger", "search_flight", etc.).

3.  Follow the prompts to enter the required information.

4.  Press Enter to execute the command.

Example Input
-------------

-   Add Flight: `add_flight` `AI602, DEL, BOM, Air India, 2022-06-24 10:00:00, 2022-06-24 12:00:00`

-   Add Passenger: `add_passenger` `akshat,akshat@gmail.com,786756453`

-   Search Flight: `search_flight` `DEL,BOM,null,null,null,null,null`

-   Book Flight: `book_flight` `AI203,akshat,PREMIUM,20`

-   Get Bookings: `get_bookings` `akshat`

-   Cancel Flight: `cancel_flight` `AI203`

-   Exit: `exit`

Notes
-----

-   The app uses a simple text-based interface and does not support complex queries or filtering.

-   The app does not store data persistently and will lose all data when it is closed.

-   The app is designed for basic use cases and may not be suitable for large-scale or complex airline reservation systems.