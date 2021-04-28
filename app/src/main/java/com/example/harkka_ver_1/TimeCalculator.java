/*
Project work
SleepTracker
Joona Saloniemi
*/


package com.example.harkka_ver_1;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeCalculator {

    Date startTime;
    Date stopTime;

    private static TimeCalculator timecalculator = new TimeCalculator();

    /*
    This method date times from getStartTime and getStopTime.
    Returns time difference ass a string to MainActivity.
    */
    public String timeBetween () {

        DateFormat df = DateFormat.getInstance();

        long difference = startTime.getTime() - stopTime.getTime();

        // Calculate hours, minutes and seconds from long difference.
        int hours = (int) ((difference - (1000*60*60*24*24*(difference / (1000*60*60*24*24)))) / (1000*60*60));
        int minutes = (int) (difference - (1000*60*60*24*(difference / (1000*60*60*24*24))) - (1000*60*60*hours)) / (1000*60);
        // The calculation for seconds is also added for making testing faster. In real life not needed.
        int seconds = (int) (difference - (1000*60*60*24*(difference / (1000*60*60*24*24))) - (1000*60*60*hours) - (1000*60*minutes)) / 1000;

        // Return integer value:
        int hoursPositive = Math.abs(hours);
        int minPositive = Math.abs(minutes);
        int secPositive = Math.abs(seconds);

        String timeDiff = String.format("%2d h %2d min %2d s", hoursPositive, minPositive, secPositive);

        return timeDiff;
    }

    public Date getStarTime () {
        startTime = Calendar.getInstance().getTime();
        return startTime;
    }

    public Date getStopTime () {
        stopTime = Calendar.getInstance().getTime();
        return stopTime;
    }

    public static TimeCalculator getInstance() {
        return timecalculator;
    }

}
