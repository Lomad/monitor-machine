package com.winning.monitor.message;

/**
 * Created by nicholasyan on 16/9/6.
 */
public interface DataEntity {

    String getMessageId();

    String getHostName();

    String getIpAddress();

    String getCollectorType();

    long getTimestamp();

}
