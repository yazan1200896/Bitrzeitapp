package com.example.bitrzeitapp;
public class Events {
    public String eventName;
    public String location;
    public String time;
    public String userId;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Events(String eventName, String location, String time, String userId) {
        this.eventName = eventName;
        this.location = location;
        this.time = time;
        this.userId=userId;

    }


    public Events() {
    }


    public String getDate() {
        return time;
    }
}

