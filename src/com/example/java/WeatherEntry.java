package com.example.java;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherEntry {
    // fields to be used from input files
    Date when;
    float pressure;
    float humidity;

    public String toString(){
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        return ("Data for: " + df.format(when)
                + " Barometric Pressure: "
                +  Float.toString(pressure)
                + " Humidity: " + Float.toString(humidity));
    }
}
