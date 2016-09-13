package com.winning.monitor.superisor.consumer.api.report;

import java.util.Map;
import java.util.Set;

public interface ReportManager<T> {

    void destory();

    void initialize();

    Set<String> getDomains(long startTime);

    T getHourlyReport(long startTime, String domain, boolean createIfNotExist);

    Map<String, T> getHourlyReports(long startTime);

    Map<String, T> loadHourlyReports(long startTime, int index);

    void storeHourlyReports(long startTime, int index);
}
