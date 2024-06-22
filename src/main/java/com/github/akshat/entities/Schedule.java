package com.github.akshat.entities;

import java.util.Date;

public class Schedule {
    private Date startTime;
    private Date endTime;

    public Schedule(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
    // getters
    public Date getStartTime() {
        return startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
}
