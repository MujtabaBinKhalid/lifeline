package com.lifeline.fyp.fyp.diet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mujtaba_khalid on 2/24/2018.
 */

public class ReportModel {

    private String totalcalories;
    private String daydate;
    private String date;
    private ArrayList<FoodEaten> reports;


    public ReportModel(String totalcalories, String daydate,String date,ArrayList<FoodEaten> reports) {
        this.totalcalories = totalcalories;
        this.daydate = daydate;
        this.date = date;
        this.reports = reports;
    }

    public String getTotalcalories() {
        return totalcalories;
    }

    public String getDaydate() {
        return daydate;
    }

    public ArrayList<FoodEaten> getReports() {
        return reports;
    }

    public String getDate() {
        return date;
    }
}
