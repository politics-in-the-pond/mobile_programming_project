package com.example.mpteam.Services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmService {
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH/mm");
    boolean isAlarmOn = false;
    public void Service(){
        while(true){
            try {
                Thread.sleep(600000);
                try {
                    Date dateTime = dateFormat.parse("21/00");
                    Date now = dateFormat.parse(dateFormat.format(System.currentTimeMillis()));
                    dateTime.compareTo(now);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
