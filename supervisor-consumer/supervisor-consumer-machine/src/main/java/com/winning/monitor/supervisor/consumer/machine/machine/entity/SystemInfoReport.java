package com.winning.monitor.supervisor.consumer.machine.machine.entity;

import com.winning.monitor.agent.collector.api.entity.CollectData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Lemod
 * @Version 2016/12/7
 */
public class SystemInfoReport {

    private String id;
    private String m_domain;
    private String ipAddress;
    private java.util.Date m_startTime;
    private java.util.Date m_endTime;
    private List<CollectData> infoList = new ArrayList<>();

    public SystemInfoReport(String m_domain) {
        this.m_domain = m_domain;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getM_domain() {
        return m_domain;
    }

    public SystemInfoReport setM_domain(String m_domain) {
        this.m_domain = m_domain;
        return this;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getM_startTime() {
        return m_startTime;
    }

    public void setM_startTime(Date m_startTime) {
        this.m_startTime = m_startTime;
    }

    public Date getM_endTime() {
        return m_endTime;
    }

    public void setM_endTime(Date m_endTime) {
        this.m_endTime = m_endTime;
    }

    public List<CollectData> getInfoList() {
        return infoList;
    }

    public SystemInfoReport addInfo(CollectData collectData){
        this.infoList.add(collectData);
        return this;
    }
}
