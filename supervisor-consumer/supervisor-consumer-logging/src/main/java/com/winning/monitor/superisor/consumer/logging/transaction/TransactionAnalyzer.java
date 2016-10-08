package com.winning.monitor.superisor.consumer.logging.transaction;


import com.winning.monitor.agent.logging.message.LogMessage;
import com.winning.monitor.agent.logging.message.MessageTree;
import com.winning.monitor.agent.logging.transaction.Transaction;
import com.winning.monitor.data.api.vo.Duration;
import com.winning.monitor.data.api.vo.Range;
import com.winning.monitor.data.api.vo.Range2;
import com.winning.monitor.data.storage.api.MessageTreeStorage;
import com.winning.monitor.superisor.consumer.api.analysis.AbstractMessageAnalyzer;
import com.winning.monitor.superisor.consumer.api.report.ReportManager;
import com.winning.monitor.superisor.consumer.logging.transaction.entity.TransactionName;
import com.winning.monitor.superisor.consumer.logging.transaction.entity.TransactionReport;
import com.winning.monitor.superisor.consumer.logging.transaction.entity.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class TransactionAnalyzer
        extends AbstractMessageAnalyzer<MessageTree> {

    private static final Logger logger = LoggerFactory.getLogger(TransactionAnalyzer.class);

    private final ReportManager<TransactionReport> reportReportManager;

    @Autowired
    private MessageTreeStorage messageTreeStorage;

    public TransactionAnalyzer(ReportManager<TransactionReport> reportReportManager) {
        this.reportReportManager = reportReportManager;
    }

    @Override
    public void doCheckpoint(boolean atEnd) {
        logger.info("TransactionAnalyzer正在执行checkpoint...");
        reportReportManager.storeHourlyReports(getStartTime(),
                ReportManager.StoragePolicy.FILE_AND_DB, m_index);
        logger.info("TransactionAnalyzer执行checkpoint成功!");
    }

    @Override
    protected void loadReports() {
        reportReportManager.loadHourlyReports(getStartTime(), m_index);
    }

    @Override
    protected void process(MessageTree tree) {
        String domain = tree.getDomain();
        TransactionReport report = reportReportManager.getHourlyReport(getStartTime(), domain, true);
        LogMessage message = tree.getMessage();

        report.addIp(tree.getIpAddress());

        if (message instanceof Transaction) {

            this.storeTransaction(tree);

            Transaction root = (Transaction) message;
            processTransaction(report, tree, root);
        }
    }

    protected void storeTransaction(MessageTree tree) {
        try {
            messageTreeStorage.storeTransaction(tree);
        } catch (Exception e) {
            logger.error("storeTransaction时发生错误", e);
        }
    }


    protected void processTransaction(TransactionReport report, MessageTree tree, Transaction t) {
        String type = t.getType();
        String name = t.getName();

//        if (m_serverFilterConfigManager.discardTransaction(type, name)) {
//            return;
//        } else {
        //Pair<Boolean, Long> pair = checkForTruncatedMessage(tree, t);

        // if (pair.getKey().booleanValue()) {

        String ip = tree.getIpAddress();
        TransactionType transactionType = report.findOrCreateMachine(ip).findOrCreateType(type);
        TransactionName transactionName = transactionType.findOrCreateName(name);
        String messageId = tree.getMessageId();

        processTypeAndName(t, transactionType, transactionName, messageId,
                t.getDurationInMicros() / 1000d);
        //}

        List<LogMessage> children = t.getChildren();

        for (LogMessage child : children) {
            if (child instanceof Transaction) {
                processTransaction(report, tree, (Transaction) child);
            }
        }
//        }
    }


    protected void processTypeAndName(Transaction t, TransactionType type, TransactionName name, String messageId,
                                      double duration) {
        type.incTotalCount();
        name.incTotalCount();

        if (t.isSuccess()) {
            if (type.getSuccessMessageUrl() == null) {
                type.setSuccessMessageUrl(messageId);
            }

            if (name.getSuccessMessageUrl() == null) {
                name.setSuccessMessageUrl(messageId);
            }
        } else {
            type.incFailCount();
            name.incFailCount();

            if (type.getFailMessageUrl() == null) {
                type.setFailMessageUrl(messageId);
            }

            if (name.getFailMessageUrl() == null) {
                name.setFailMessageUrl(messageId);
            }
        }


        int allDuration = ((int) computeDuration(duration));
        double sum = duration * duration;

        name.setMax(Math.max(name.getMax(), duration));
        name.setMin(Math.min(name.getMin(), duration));
        name.setSum(name.getSum() + duration);
        name.setSum2(name.getSum2() + sum);
        name.findOrCreateAllDuration(allDuration).incCount();

        type.setMax(Math.max(type.getMax(), duration));
        type.setMin(Math.min(type.getMin(), duration));
        type.setSum(type.getSum() + duration);
        type.setSum2(type.getSum2() + sum);
        type.findOrCreateAllDuration(allDuration).incCount();

        long current = t.getTimestamp() / 1000 / 60;
        int min = (int) (current % (60));

        processNameGraph(t, name, min, duration);
        processTypeRange(t, type, min, duration);

        //this.reportReportManager.storeHourlyReports(this.getStartTime(), m_index);
    }

    private double computeDuration(double duration) {
        if (duration < 20) {
            return duration;
        } else if (duration < 200) {
            return duration - duration % 5;
        } else if (duration < 2000) {
            return duration - duration % 50;
        } else {
            return duration - duration % 500;
        }
    }

    private void processNameGraph(Transaction t, TransactionName name, int min, double d) {
        int dk = 1;

        if (d > 65536) {
            dk = 65536;
        } else {
            if (dk > 256) {
                dk = 256;
            }
            while (dk < d) {
                dk <<= 1;
            }
        }

        Duration duration = name.findOrCreateDuration(dk);
        Range range = name.findOrCreateRange(min);

        duration.incCount();
        range.incCount();

        if (!t.isSuccess()) {
            range.incFails();
        }

        range.setSum(range.getSum() + d);
    }

    private void processTypeRange(Transaction t, TransactionType type, int min, double d) {
        Range2 range = type.findOrCreateRange2(min);

        if (!t.isSuccess()) {
            range.incFails();
        }

        range.incCount();
        range.setSum(range.getSum() + d);
    }

}
