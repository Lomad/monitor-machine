package com.winning.monitor.superisor.consumer.logging.transaction.dto;

import java.util.List;

/**
 * Created by nicholasyan on 16/9/12.
 */
public class TransactionReportDTO {

    private String id;
    private String domain;
    private List<String> ips;
    private String startTime;
    private String endTime;
    private List<TransactionMachineDTO> machines;

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

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
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

    public List<TransactionMachineDTO> getMachines() {
        return machines;
    }

    public void setMachines(List<TransactionMachineDTO> machines) {
        this.machines = machines;
    }
}
