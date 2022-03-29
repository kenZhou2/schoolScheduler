package com.example.schoolscheduler;

import java.util.List;

/*
    Simple data structure to hold information about the courses added to the app. This will be added
    to the Firebase data store, which can hold entire objects together.

    If you're wondering why the name isn't being stored in this structure, it's because the name is
    used as the key in the Firebase data store to keep the flattened version of these data together.
 */

public class Course {


    private String name;
    private String startTime;
    private String endTime;
    private List<String> days;

    // We're only going to instantiate this object once all of the data has been filled in.
    public Course(String name,
                  String startTime,
                  String endTime,
                  List<String> days) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.days = days;
    }

    public Course() {
        // No value constructor required for Firebase.
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public List<String> getDays() {
        return days;
    }
}