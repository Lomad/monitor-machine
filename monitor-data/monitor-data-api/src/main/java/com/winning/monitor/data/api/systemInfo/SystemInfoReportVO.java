package com.winning.monitor.data.api.systemInfo;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author Lemod
 * @Version 2016/12/7
 */
public class SystemInfoReportVO {

    private String id;
    private String ipAddress;
    private String hostName;
    private String startTime;
    private String endTime;
    private List<LinkedHashMap<String,Object>> infoList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String domain) {
        this.ipAddress = domain;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<LinkedHashMap<String, Object>> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<LinkedHashMap<String, Object>> infoList) {
        this.infoList = infoList;
    }

    public void addInfoList(List<LinkedHashMap<String, Object>> infoList){
        this.infoList.addAll(infoList);
    }
}
