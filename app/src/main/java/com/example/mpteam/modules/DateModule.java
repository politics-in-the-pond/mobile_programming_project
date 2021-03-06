package com.example.mpteam.modules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateModule {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    static SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH/mm");

    public static int compareDay(String dayOrigin, String dayNew) { //origin : 10일 new : 12일 일때, 2 반환 //0반환되면 같은 날
        if (dayOrigin.equals("")) {
            return 1;
        }
        if(dayNew.equals("")) {
            return -1;
        }
        Date origin = null;
        try {
            origin = dateFormat.parse(dayOrigin);
            Date compare = dateFormat.parse(dayNew);
            long calDate = compare.getTime() - origin.getTime();
            long calDateDays = calDate / ( 24*60*60*1000);
            return (int) calDateDays;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int compareDay2(String dayOrigin, String dayNew) { //origin : 10일 new : 12일 일때, 2 반환 //0반환되면 같은 날
        if (dayOrigin.equals("")) {
            return 1;
        }
        if(dayNew.equals("")) {
            return -1;
        }
        Date origin = null;
        try {
            origin = dateFormat2.parse(dayOrigin);
            Date compare = dateFormat2.parse(dayNew);
            return origin.compareTo(compare);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getToday() {
        return dateFormat.format(System.currentTimeMillis());
    }
}
