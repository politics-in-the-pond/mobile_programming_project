package com.example.mpteam.modules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateModule {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public static int compareDay(String dayOrigin, String dayNew) { //origin : 10일 new : 12일 일때, 2 반환
        if(dayOrigin.equals("")){
            return 1;
        }
        Date origin = null;
        try {
            origin = dateFormat.parse(dayOrigin);
            Date compare = dateFormat.parse(dayNew);
            return compare.compareTo(origin);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getToday(){
        return dateFormat.format(System.currentTimeMillis());
    }
}
