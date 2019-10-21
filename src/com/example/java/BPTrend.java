package com.example.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
                    entry.when = format.parse(wordsArray[1]);
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


}
