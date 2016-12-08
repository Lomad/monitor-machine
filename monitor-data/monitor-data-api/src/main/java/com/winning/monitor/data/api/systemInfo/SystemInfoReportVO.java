package com.winning.monitor.data.api.systemInfo;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author Lemod
 * @Version 2016/12/7
 */
public class SystemInfoReportVO {

    private String id;
    private String domain;
    private String ipAddress;
    private String startTime;
    private String endTime;
    private List<LinkedHashMap<String,Object>> infoList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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
}
