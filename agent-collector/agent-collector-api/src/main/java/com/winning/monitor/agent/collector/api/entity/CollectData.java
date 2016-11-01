package com.winning.monitor.agent.collector.api.entity;

import com.winning.monitor.message.DataEntity;

import java.util.LinkedHashMap;

/**
 * Created by nicholasyan on 16/8/26.
 */
public class CollectData extends LinkedHashMap<String, Object>
        implements DataEntity {

    private final String collectorType;
    private String serverName;
    private String messageId;
    private String hostName;
    private String ipAddress;

    private long timestamp;

    public CollectData(String collectorType) {
        this.collectorType = collectorType;
        this.timestamp = System.currentTimeMillis();
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String getHostName() {
        return this.hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @Override
    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCollectorType() {
        return this.collectorType;
    }

    @Override
    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
