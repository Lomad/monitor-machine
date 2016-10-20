package com.winning.monitor.data.api.transaction.domain;

import java.util.LinkedHashMap;

/**
 * Created by nicholasyan on 16/10/20.
 */
public class TransactionCallTimesReport {

    //持续时间内的调用次数结果集,Key代表分钟数或日期数,Value代表调用的次数
    private LinkedHashMap<Integer, Long> durations;

    public LinkedHashMap<Integer, Long> getDurations() {
        return durations;
    }

    public void setDurations(LinkedHashMap<Integer, Long> durations) {
        this.durations = durations;
    }
}
