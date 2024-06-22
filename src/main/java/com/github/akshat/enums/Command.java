package com.github.akshat.enums;

import java.util.HashMap;
import java.util.Map;

public enum Command {
    ADD_FLIGHT,
    ADD_PASSENGER,
    SEARCH_FLIGHT,
    BOOK_FLIGHT,
    GET_BOOKINGS,
    CANCEL_FLIGHT,
    EXIT;


    private String command;

    Command() {
        this.command = this.name().toLowerCase();
    }

    // support lookup of string into enum  using a hash map
    private static final Map<String, Command> lookup = new HashMap<>();
    static {
        for (Command command : Command.values()) {
            lookup.put(command.toString(), command);
        }
    }

    // get command from string
    public static Command get(String command) {
        return lookup.get(command.toUpperCase());
    }


}
