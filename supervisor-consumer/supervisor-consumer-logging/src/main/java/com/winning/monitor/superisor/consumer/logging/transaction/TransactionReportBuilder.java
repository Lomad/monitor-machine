package com.winning.monitor.superisor.consumer.logging.transaction;

import com.winning.monitor.data.api.transaction.HistoryTransactionReportMerger;
import com.winning.monitor.data.api.transaction.vo.TransactionReportType;
import com.winning.monitor.data.api.transaction.vo.TransactionReportVO;
import com.winning.monitor.data.storage.api.ITransactionDataStorage;
import com.winning.monitor.supervisor.core.helper.TimeHelper;
import com.winning.monitor.supervisor.core.task.TaskBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class TransactionReportBuilder implements TaskBuilder {

    public final static String TASK_BUILDER_NAME = "TransactionReportBuilder";
    private static final Logger logger = LoggerFactory.getLogger(TransactionReportBuilder.class);

    @Autowired
    private ITransactionDataStorage transactionDataStorage;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String getName() {
        return TASK_BUILDER_NAME;
    }

    @Override
    public boolean buildDailyTask(String name, String domain, Date period) {
        return false;
//        try {
//            Date end = TaskHelper.tomorrowZero(period);
//            TransactionReport transactionReport = queryHourlyReportsByDuration(domain, period, end);
//
//            buildDailyTransactionGraph(transactionReport);
//
//            DailyReport report = new DailyReport();
//
//            report.setCreationDate(new Date());
//            report.setDomain(domain);
//            report.setIp(NetworkInterfaceManager.INSTANCE.getLocalHostAddress());
//            report.setName(name);
//            report.setPeriod(period);
//            report.setType(1);
//            byte[] binaryContent = DefaultNativeBuilder.build(transactionReport);
//            return m_reportService.insertDailyReport(report, binaryContent);
//        } catch (Exception e) {
//            m_logger.error(e.getMessage(), e);
//            Cat.logError(e);
//            return false;
//        }
    }

    @Override
    public boolean buildHourlyTask(String name, String domain, Date period) {
        try {
            long startTime = period.getTime();
            Date endTime = new Date(startTime + TimeHelper.ONE_HOUR);
            TransactionReportVO report = this.queryHourlyReport(domain, new Date(startTime), endTime);

            report.setEndTime(this.sdf.format(endTime));
            report.setType(TransactionReportType.HOURLY);
            this.saveHistoryTransactionReport(report);

            logger.info("{}的小时报表生成报表成功!", domain);

            return true;
        } catch (Exception e) {
            logger.error("TransactionReportBuilder.buildHourlyTask生成报表时发生错误", e);
        }
        return false;
    }

    @Override
    public boolean buildMonthlyTask(String name, String domain, Date period) {
        return false;
    }

    @Override
    public boolean buildWeeklyTask(String name, String domain, Date period) {
        return false;
    }

    public TransactionReportVO queryHourlyReport(String domain, Date start, Date end) {

        String kssj = this.sdf.format(start);
        String jssj = this.sdf.format(end);

        //查询出所有时间段内的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryTransactionReports(domain,
                        kssj, jssj, TransactionReportType.REALTIME);

        HistoryTransactionReportMerger transactionReportMerger = new HistoryTransactionReportMerger(0, 1);
        TransactionReportVO transactionReport = transactionReportMerger.mergeReports(reports);
        return transactionReport;
    }

    private void saveHistoryTransactionReport(TransactionReportVO transactionReportVO) {
        this.transactionDataStorage.storeHistoryTransactionReport(transactionReportVO);
    }


//    private TransactionReport queryHourlyReportsByDuration(String domain, Date start, Date endDate) {
//        long startTime = start.getTime();
//        long endTime = endDate.getTime();
//        double duration = (endTime - startTime) * 1.0 / TimeHelper.ONE_DAY;
//
//        String kssj = this.sdf.format(start);
//        String jssj = this.sdf.format(endDate);
//
//        List<TransactionReportVO> reports =
//                this.transactionDataStorage.queryAllDomainTransactionReports(kssj, jssj);
//
//        for (; startTime < endTime; startTime = startTime + TimeHelper.ONE_HOUR) {
//            TransactionReport report = m_reportService.queryReport(domain, new Date(startTime), new Date(
//                    startTime + TimeHelper.ONE_HOUR));
//
//            reports.add(report);
//        }
//        return m_transactionMerger.mergeForDaily(domain, reports, domainSet, duration);
//    }

}
