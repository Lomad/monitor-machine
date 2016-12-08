package com.winning.monitor.supervisor.consumer.machine.machine;

import com.winning.monitor.data.api.systemInfo.SystemInfoReportVO;
import com.winning.monitor.data.storage.api.ISystemInfoStorage;
import com.winning.monitor.superisor.consumer.api.report.AbstractReportManager;
import com.winning.monitor.supervisor.consumer.machine.machine.entity.SystemInfoReport;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @Author Lemod
 * @Version 2016/12/7
 */
public class CollectDataReportManager extends AbstractReportManager<SystemInfoReport> implements Runnable{

    private Thread thread;
    private volatile boolean available = true;

    @Autowired
    private ISystemInfoStorage systemInfoStorage;

    @PostConstruct
    public void initialize(){
        if (thread != null)
            return;

        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    protected SystemInfoReport makeReport(String group, String domain, long startTime, long duration) {
        SystemInfoReport infoReport = new SystemInfoReport(domain);
        infoReport.setId(UUID.randomUUID().toString());
        infoReport.setM_startTime(new Date(startTime));
        infoReport.setM_endTime(new Date(startTime+duration-1));

        return infoReport;
    }

    @Override
    public void run() {
        while (available) {
            try {
                for (long startTime : this.m_reports.keySet()) {
                    this.storeHourlyReports(startTime, StoragePolicy.FILE, 0);
                }
            } catch (Exception e) {

            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void storeHourlyReports(long startTime, StoragePolicy storagePolicy, int index) {
        Map<String,SystemInfoReport> reports = m_reports.get(startTime);
        if (reports == null || reports.isEmpty())
            return;

        try {
            for (SystemInfoReport systemInfoReport : reports.values()) {
                SystemInfoReportVO systemInfoReportVO = CollectDataReportConverter.toSystemInfoReportVO(systemInfoReport);

                this.systemInfoStorage.storeCollectDataReport(systemInfoReportVO);
            }
        }catch (Exception e){

        }
    }

    @Override
    public Map<String, SystemInfoReport> loadHourlyReports(long startTime, int index) {
        return null;
    }

    @PreDestroy
    public void shutdown(){
        this.available = false;
    }
}
