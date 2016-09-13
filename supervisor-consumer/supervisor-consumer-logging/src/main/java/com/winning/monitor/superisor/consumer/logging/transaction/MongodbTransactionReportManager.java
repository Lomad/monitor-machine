package com.winning.monitor.superisor.consumer.logging.transaction;

import com.winning.monitor.agent.config.utils.NetworkInterfaceManager;
import com.winning.monitor.superisor.consumer.api.report.AbstractReportManager;
import com.winning.monitor.superisor.consumer.logging.transaction.dto.TransactionReportConverter;
import com.winning.monitor.superisor.consumer.logging.transaction.dto.TransactionReportDTO;
import com.winning.monitor.superisor.consumer.logging.transaction.entity.TransactionReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by nicholasyan on 16/9/12.
 */
public class MongodbTransactionReportManager extends AbstractReportManager<TransactionReport>
        implements Runnable {

    private Thread thread;
    private volatile boolean active = true;

    @Autowired
    private MongoTemplate mongoTemplate;

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
    public void storeHourlyReports(long startTime, int index) {
        Map<String, TransactionReport> reports = m_reports.get(startTime);
        if (reports == null)
            return;

        for (TransactionReport transactionReport : reports.values()) {
            TransactionReportDTO transactionReportDTO =
                    TransactionReportConverter.toTransactionReportDTO(transactionReport);

            Query query = new Query();
            query.addCriteria(new Criteria("id").is(transactionReport.getId()));
            Update update = new Update();

            update.set("id", transactionReportDTO.getId());
            update.set("domain", transactionReportDTO.getDomain());
            update.set("idx", index);
            update.set("ips", transactionReportDTO.getIps());
            update.set("type", "hourly");
            update.set("server", NetworkInterfaceManager.INSTANCE.getLocalHostAddress());
            update.set("startTime", transactionReportDTO.getStartTime());
            update.set("endTime", transactionReportDTO.getEndTime());
            update.set("machines", transactionReportDTO.getMachines());

            this.mongoTemplate.upsert(query, update, "transactionReports");
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
        try {
            while (active) {
                for (long startTime : this.m_reports.keySet()) {
                    this.storeHourlyReports(startTime, 0);
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {

        }
    }

}
