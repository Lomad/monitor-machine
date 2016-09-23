package com.winning.monitor.data.api.transaction.vo;

/**
 * Created by nicholasyan on 16/9/14.
 */
public enum TransactionReportType {

    REALTIME("REALTIME"), HOURLY("HOURLY"), DAILY("DAILY"), WEEKLY("WEEKLY"), MONTHLY("MONTHLY");

    private String m_name;

    TransactionReportType(String name) {
        m_name = name;
    }

    public String getName() {
        return m_name;
    }
}
