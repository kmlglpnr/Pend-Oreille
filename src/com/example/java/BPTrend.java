package com.example.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BPTrend {
    ArrayList<WeatherEntry> collectedData = new ArrayList<>();

    // first step is reading our data
    public void readData(String filename){
        DateFormat format = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
        // try-with-resources
        try(
                BufferedReader buf = new BufferedReader(new FileReader(filename));
                )
        {
            String line = null;
            String[] wordsArray;
            boolean skipFirstLine = true;

            while(true){
                line = buf.readLine();
                if (skipFirstLine){ // skip data header
                    skipFirstLine = false;
                    continue; // skipping the first line
                              // going to the next while(true) loop step
                }
                if (line == null){
                    break;
                } else {
                    wordsArray = line.split("\t");
                    // desired fields from input text and WeatherEntry instance
                    WeatherEntry entry = new WeatherEntry();
                    entry.when = format.parse(wordsArray[0]);
                    entry.pressure = Float.valueOf(wordsArray[2]);
                    entry.humidity = Float.valueOf(wordsArray[4]);
                    collectedData.add(entry);

                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // slope calculation
    public String doSlopeCalculation(String from, String to){
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;
        try{
            d1 = format.parse(from);
            d2 = format.parse(to);
        } catch (Exception e){
            e.printStackTrace();
        }
        String result  = "From "
                + format.format(d1)
                + " To "
                + format.format(d2);

        WeatherEntry y1 = null;
        WeatherEntry y2 = null;
        int idx = 0, x1 = 0, x2 = 0;

        for(WeatherEntry entry : collectedData){
            // if d1 is same or before than entry.when
            if( (y1 == null) && (entry.when.compareTo(d1) >= 0)){
                y1 = entry;
                x1 = idx;
            }
            // if d2 is same or before than entry.when
            if (entry.when.compareTo(d2) >= 0){
                x2 = idx;
                y2 = entry;
                break;
            }
            idx++;
        }
        // formula, slope varible
        float slope = (y2.pressure - y1.pressure) / (x2 - x1);

        result = result + " The barometric pressure slope is "
                + String.format("%.6f",slope) + " \nthe foreccast is: ";

        // Trend analysis
        if(slope < 0 ) {
            result = result + "inclement weather is closing in \n";
        } else if(slope == 0){
            result = result + "current conditions are likely to persist.\n";
        } else {
            result  = result + "conditions are improving.\n";
        }
        return result;
    }

    public static void main(String[] args) {
        BPTrend calcTrend = new BPTrend();
        System.out.println("Reading data...");
        calcTrend.readData("files/Environmental_Data_Deep_Moor_2012.txt");
        calcTrend.readData("files/Environmental_Data_Deep_Moor_2013.txt");
        calcTrend.readData("files/Environmental_Data_Deep_Moor_2014.txt");
        calcTrend.readData("files/Environmental_Data_Deep_Moor_2015.txt");
        System.out.println("Done!");
        System.out.println("Total number of weather data entries: "
        + calcTrend.collectedData.size());

        String from = "";
        String to = "";

        System.out.println("Test Case #1: ");
        from = "2012/01/01 00:30:00";
        to = "2012/01/01 02:30:00";

        System.out.println(calcTrend.doSlopeCalculation(from, to));

        System.out.println("Test Case #2: ");
        from = "2013/03/15 10:30:00";
        to = "2013/03/17 02:30:00";

        System.out.println(calcTrend.doSlopeCalculation(from, to));

    }

}
