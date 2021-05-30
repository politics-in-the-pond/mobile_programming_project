package com.example.mpteam.data;

import java.io.Serializable;

public class DiaryStreak implements Serializable {
    String userId, lastDay; //simple data format "YYYY/MM/DD"
    int gauge; //gauge%7 = left gauge, (int) gauge/7 = coin

    public DiaryStreak(String userId, String lastDay, int gauge) {
        this.userId = userId;
        this.lastDay = lastDay;
        this.gauge = gauge;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastDay() {
        return lastDay;
    }

    public void setLastDay(String lastDay) {
        this.lastDay = lastDay;
    }

    public int getGauge() {
        return gauge;
    }

    public void setGauge(int gauge) {
        this.gauge = gauge;
    }
}
