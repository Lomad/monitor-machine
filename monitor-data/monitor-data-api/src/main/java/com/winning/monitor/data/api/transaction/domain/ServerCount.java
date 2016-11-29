package com.winning.monitor.data.api.transaction.domain;

/**
 * Created by sao something on 2016/11/24.
 */
public class ServerCount {
    private long todayCount;
    private long yestodayCount;

    public long getTodayCount() {
        return todayCount;
    }

    public void setTodayCount(long todayCount) {
        this.todayCount = todayCount;
    }

    public long getYestodayCount() {
        return yestodayCount;
    }

    public void setYestodayCount(long yestodayCount) {
        yestodayCount = yestodayCount;
    }
}
