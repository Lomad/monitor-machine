package com.winning.monitor.data.api.transaction.domain;

/**
 * Created by sao something on 2016/11/24.
 */
public class ServerCount {
    private int todayCount;
    private int yestodayCount;

    public int getTodayCount() {
        return todayCount;
    }

    public void setTodayCount(int todayCount) {
        this.todayCount = todayCount;
    }

    public int getYestodayCount() {
        return yestodayCount;
    }

    public void setYestodayCount(int yestodayCount) {
        yestodayCount = yestodayCount;
    }
}
