package com.github.akshat.exceptions;

/**
 * @author akshatgupta
 * @Date 25/06/24
 */

public class DataNotFoundException extends Exception{
    private static final long serialVersionUID = -4432292346834265371L;

    public DataNotFoundException(String message) {
        super(message);
    }

}
