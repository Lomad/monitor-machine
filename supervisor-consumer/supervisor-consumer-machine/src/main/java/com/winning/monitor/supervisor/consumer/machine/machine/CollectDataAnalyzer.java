package com.winning.monitor.supervisor.consumer.machine.machine;

import com.winning.monitor.agent.collector.api.entity.CollectData;
import com.winning.monitor.agent.collector.api.entity.CollectDatas;
import com.winning.monitor.superisor.consumer.api.analysis.AbstractMessageAnalyzer;
import com.winning.monitor.superisor.consumer.api.report.ReportManager;
import com.winning.monitor.supervisor.consumer.machine.machine.entity.SystemInfoReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Lemod
 * @Version 2016/12/7
 */
public class CollectDataAnalyzer extends AbstractMessageAnalyzer<CollectDatas> {

    private static final Logger logger = LoggerFactory.getLogger(CollectDataAnalyzer.class);

    private final ReportManager<SystemInfoReport> collectReportManager;

    public CollectDataAnalyzer(ReportManager<SystemInfoReport> collectDatasReportManager) {
        this.collectReportManager = collectDatasReportManager;
    }

    @Override
    public void doCheckpoint(boolean atEnd) {
        logger.info("分析器正在执行存储...");
        this.collectReportManager.storeHourlyReports(getStartTime(),
                ReportManager.StoragePolicy.FILE_AND_DB,m_index);
        logger.info("分析器执行存储成功!");
    }

    @Override
    protected void process(CollectDatas message) {
        String domain = message.getHostName();
        SystemInfoReport infoReport = collectReportManager.getHourlyReport(getStartTime(), null,domain, true);

        if (message.getDatas().size()>0){
            for (CollectData collectData : message.getDatas()){
                infoReport.addInfo(collectData);
            }

            infoReport.setIpAddress(message.getIpAddress());
        }
    }

    @Override
    protected void loadReports() {
        this.collectReportManager.loadHourlyReports(getStartTime(), m_index);
    }
}
