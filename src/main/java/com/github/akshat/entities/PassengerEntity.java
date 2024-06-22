package com.github.akshat.entities;

import lombok.Builder;
import lombok.Data;

/**
 * @author akshatgupta
 * @Date 22/06/24
 */

@Data
public class PassengerEntity {
    private static int count = 0;

    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;

    public PassengerEntity(String name, String email, String phoneNumber) {
        this.id = ++count; // can be auto incremented
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


    @Override
    public String toString() {
        return "PassengerEntity{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
