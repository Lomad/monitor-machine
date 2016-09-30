package com.winning.monitor.superisor.consumer.logging.transaction;

import com.winning.monitor.agent.config.utils.NetworkInterfaceManager;
import com.winning.monitor.data.api.transaction.vo.TransactionReportType;
import com.winning.monitor.data.api.transaction.vo.TransactionReportVO;
import com.winning.monitor.data.storage.api.ITransactionDataStorage;
import com.winning.monitor.superisor.consumer.api.report.AbstractReportManager;
import com.winning.monitor.superisor.consumer.logging.transaction.entity.TransactionReport;
import com.winning.monitor.supervisor.core.task.TaskManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by nicholasyan on 16/9/12.
 */
public class TransactionReportManager extends AbstractReportManager<TransactionReport>
        implements Runnable {

    private Thread thread;
    private volatile boolean active = true;

    @Autowired
    private ITransactionDataStorage transactionDataStorage;

    @Autowired
    private TaskManager taskManager;

    @Override
    protected TransactionReport makeReport(String domain, long startTime, long duration) {
        TransactionReport report = new TransactionReport(domain);
        report.setId(UUID.randomUUID().toString());
        report.setStartTime(new Date(startTime));
        report.setEndTime(new Date(startTime + duration - 1));
        return report;
    }

    @Override
    public Map<String, TransactionReport> loadHourlyReports(long startTime, int index) {
//        Map<String, TransactionReport> reports = m_reports.get(startTime);
//        if (reports == null) {
//            reports = new ConcurrentHashMap<>();
//            m_reports.put(startTime, reports);
//        }
//
//        try {
//            Query query = new Query();
//            String start = TransactionReportConverter.simpleDateTimeFormat.format(new Date(startTime));
//            query.addCriteria(new Criteria("startTime").is(start));
//            query.addCriteria(new Criteria("type").is("hourly"));
//            query.addCriteria(new Criteria("server").is(NetworkInterfaceManager.INSTANCE.getLocalHostAddress()));
//            query.addCriteria(new Criteria("idx").is(index));
//            List<TransactionReport> transactionReportList = mongoTemplate.find(query, TransactionReport.class);
//
//            for (TransactionReport transactionReport : transactionReportList) {
//                reports.put(transactionReport.getDomain(), transactionReport);
//            }
//
//        } catch (Exception e) {
//
//        }
//
//        return reports;
        return null;
    }

    @Override
    public void storeHourlyReports(long startTime, StoragePolicy storagePolicy, int index) {
        Map<String, TransactionReport> reports = m_reports.get(startTime);
        if (reports == null)
            return;

        for (TransactionReport transactionReport : reports.values()) {
            TransactionReportVO transactionReportVO =
                    TransactionReportConverter.toTransactionReportVO(transactionReport);

            transactionReportVO.setIndex(index);
            transactionReportVO.setServer(NetworkInterfaceManager.INSTANCE.getLocalHostAddress());
            transactionReportVO.setType(TransactionReportType.REALTIME);

            // TODO: 16/9/14 加入判断,未更新数据的不需要进行保存处理
            transactionDataStorage.storeTransactionReport(transactionReportVO);

            if (storagePolicy.forDatabase()) {
                try {
                    taskManager.createTask(new Date(startTime), transactionReport.getDomain(),
                            TransactionReportBuilder.TASK_BUILDER_NAME, TaskManager.TaskProlicy.ALL);
                } catch (Exception e) {

                }
            }
        }

        this.cleanup(startTime);
    }


    @PostConstruct
    public void initialize() {
        if (thread != null)
            return;

        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @PreDestroy
    public void shutdown() {
        active = false;
    }


    @Override
    public void run() {
        while (active) {
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

}