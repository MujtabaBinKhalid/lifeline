package com.lifeline.fyp.fyp.fitness;

/**
 * Created by Mujtaba_khalid on 3/13/2018.
 */

public class FitnessObject {

    private String distance;
    private String time;
    private String speed;
    private String cal;
    private String Date;

    public FitnessObject(String distance, String time, String speed, String cal, String date) {
        this.distance = distance;
        this.time = time;
        this.speed = speed;
        this.cal = cal;
        Date = date;
    }


    public String getDistance() {
        return distance;
    }

    public String getTime() {
        return time;
    }

    public String getSpeed() {
        return speed;
    }

    public String getCal() {
        return cal;
    }

    public String getDate() {
        return Date;
    }
}
