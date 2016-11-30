package com.winning.monitor.data.api.transaction.domain;

import java.util.Map;

/**
 * Created by sao something on 2016/11/25.
 */
public class WrongMessage {
    private String domain;
    private String transactionTypeName;
    private String serverIpAddress;
    private String currentTime;
   //key="error"是异常，key="alarm"是预警，value为异常或告警描述信息
    private Map<String,String> tips;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    public void setTransactionTypeName(String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
    }

    public String getServerIpAddress() {
        return serverIpAddress;
    }

    public void setServerIpAddress(String serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public Map<String, String> getTips() {
        return tips;
    }

    public void setTips(Map<String, String> tips) {
        this.tips = tips;
    }
}
