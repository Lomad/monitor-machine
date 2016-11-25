package com.winning.monitor.data.api.transaction.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sao something on 2016/11/25.
 */
public class ServerCountWithDomain {
    private String serverAppName;
    private Long todayCount;
    private Long todayRightCount;
    private Long todayWrongCount;


    public String getServerAppName() {
        return serverAppName;
    }

    public void setServerAppName(String serverAppName) {
        this.serverAppName = serverAppName;
    }

    public Long getTodayCount() {
        return todayCount;
    }

    public void setTodayCount(Long todayCount) {
        this.todayCount = todayCount;
    }

    public Long getTodayRightCount() {
        return todayRightCount;
    }

    public void setTodayRightCount(Long todayRightCount) {
        this.todayRightCount = todayRightCount;
    }

    public Long getTodayWrongCount() {
        return todayWrongCount;
    }

    public void setTodayWrongCount(Long todayWrongCount) {
        this.todayWrongCount = todayWrongCount;
    }


}
