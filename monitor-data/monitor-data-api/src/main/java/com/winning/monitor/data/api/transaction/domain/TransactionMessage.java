package com.winning.monitor.data.api.transaction.domain;

import java.util.List;
import java.util.Map;

/**
 * 事务明细记录
 */
public class TransactionMessage {

    private List<TransactionMessage> children;

    //开始时间yyyy-MM-dd HH:mm:ss
    private String startTime;

    //服务类别名称
    private String transactionTypeName;
    //服务步骤名称
    private String transactionName;
    //服务对应的系统名称
    private String serverAppName;
    //服务端IP地址
    private String serverIpAddress;
    //客户端应用名称
    private String clientAppName;
    //客户端IP地址
    private String clientIpAddress;
    //客户端类型
    private String clientType;
    //耗时(毫秒)
    private double useTime;
    //状态,成功,失败
    private String status;
    //错误消息
    private String errorMessage;
    //记录值
    private Map<String, String> datas;

    public List<TransactionMessage> getChildren() {
        return children;
    }

    public void setChildren(List<TransactionMessage> children) {
        this.children = children;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    public void setTransactionTypeName(String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getServerAppName() {
        return serverAppName;
    }

    public void setServerAppName(String serverAppName) {
        this.serverAppName = serverAppName;
    }

    public String getServerIpAddress() {
        return serverIpAddress;
    }

    public void setServerIpAddress(String serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
    }

    public String getClientAppName() {
        return clientAppName;
    }

    public void setClientAppName(String clientAppName) {
        this.clientAppName = clientAppName;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public double getUseTime() {
        return useTime;
    }

    public void setUseTime(double useTime) {
        this.useTime = useTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, String> getDatas() {
        return datas;
    }

    public void setDatas(Map<String, String> datas) {
        this.datas = datas;
    }
}
