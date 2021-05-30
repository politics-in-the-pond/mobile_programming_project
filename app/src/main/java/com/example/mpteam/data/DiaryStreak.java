package com.example.mpteam.data;

import java.io.Serializable;

public class DiaryStreak implements Serializable {
    String userId, startDay, lastDay; //simple data format "YYYY/MM/DD"
    int gauge, period; //gauge%7 = left gauge, (int) gauge/7 = coin

    public DiaryStreak(String userId, String startDay, String lastDay, int gauge, int period) {
        this.userId = userId;
        this.startDay = startDay;
        this.lastDay = lastDay;
        this.gauge = gauge;
        this.period = period;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
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
