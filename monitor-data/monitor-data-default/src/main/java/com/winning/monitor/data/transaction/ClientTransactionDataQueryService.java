package com.winning.monitor.data.transaction;

import com.winning.monitor.data.api.IClientTransactionDataQueryService;
import com.winning.monitor.data.api.transaction.domain.TransactionCallTimesReport;
import com.winning.monitor.data.api.transaction.domain.TransactionStatisticReport;
import com.winning.monitor.data.api.transaction.vo.*;
import com.winning.monitor.data.api.vo.Range2;
import com.winning.monitor.data.storage.api.ITransactionDataStorage;
import com.winning.monitor.data.transaction.builder.ClientCallTransactionTypeStatisticDataMerger;
import com.winning.monitor.data.transaction.builder.TransactionCallTimesMerger;
import com.winning.monitor.data.transaction.builder.TransactionStatisticDataMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangwh on 2016/11/2.
 */
@Service
public class ClientTransactionDataQueryService implements IClientTransactionDataQueryService{

    private static final long HOUR = 3600 * 1000L;
    private static final long DAY = HOUR * 24;


    @Autowired
    private ITransactionDataStorage transactionDataStorage;


    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 获取所有消费者务系统名称
     *
     * @param group               系统类别
     * @return
     */
    @Override
    public LinkedHashSet<String> getAllClientNames(String group) {

        return transactionDataStorage.findAllTransactionClients(group);
    }

    /**
     * 获取最近一小时的消费系统调用服务的统计结果,根据客户端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param clientAppName   消费系统名称
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryLastHourClientReportByClient(String group, String clientAppName) {
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryRealtimeTransactionReports(group, clientAppName, this.getCurrentHour());

        ClientCallTransactionTypeStatisticDataMerger merger = new ClientCallTransactionTypeStatisticDataMerger(clientAppName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }

    /**
     * 获取当天的消费系统调用服务的统计结果,根据客户端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param clientAppName   消费系统名称
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryTodayClientTypeReportByClient(String group, String clientAppName) {
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryRealtimeTransactionReports(group, clientAppName,
                        this.getToday(), this.getCurrentHour());
        ClientCallTransactionTypeStatisticDataMerger merger = new ClientCallTransactionTypeStatisticDataMerger(clientAppName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }

    /**
     * 获取指定小时的消费系统调用服务的统计结果,根据客户端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param clientAppName   消费系统名称
     * @param hour          指定小时,格式为 yyyy-MM-dd HH:mm:ss
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryHourClientReportByClient(String group, String clientAppName, String hour) {
        String startTime = hour.replace(hour.substring(14,19),"00:00");
        String endTime = hour.replace(hour.substring(14,19),"59:59");

        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, clientAppName,startTime,endTime,
                        TransactionReportType.HOURLY);

        ClientCallTransactionTypeStatisticDataMerger merger = new ClientCallTransactionTypeStatisticDataMerger(clientAppName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }

    /**
     * 获取指定日期的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param clientAppName   消费系统名称
     * @param date          指定日期,格式为 yyyy-MM-dd
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryDayClientReportByClient(String group, String clientAppName, String date) {

        String startTime = date  +  " " + "00:00:00";
        String endTime = date  +  " " + "23:59:59";

        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, clientAppName,startTime,endTime,
                        TransactionReportType.DAILY);

        ClientCallTransactionTypeStatisticDataMerger merger = new ClientCallTransactionTypeStatisticDataMerger(clientAppName);
        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;

    }

    /**
     * 获取指定周的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param clientAppName   消费系统名称
     * @param week          指定周的第一天日期,格式为 yyyy-MM-dd
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryWeekClientReportByClient(String group, String clientAppName, String week) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,Integer.parseInt(week.substring(0,4)));
        cal.set(Calendar.MONTH,Integer.parseInt(week.substring(5,7))-1);
        cal.set(Calendar.DATE,Integer.parseInt(week.substring(8,10)));
        cal.add(Calendar.DATE,6);

        String startTime = week + " 00:00:00";
        String endTime = simp.format(cal.getTime()) + " 23:59:59";
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, clientAppName,startTime,endTime,
                        TransactionReportType.WEEKLY);

        ClientCallTransactionTypeStatisticDataMerger merger = new ClientCallTransactionTypeStatisticDataMerger(clientAppName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }

    /**
     * 获取指定月的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param clientAppName   消费系统名称
     * @param month         指定月份的第一条日期,格式为 yyyy-MM-dd
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryMonthClientReportByClient(String group, String clientAppName, String month) {
        Calendar calendar = Calendar.getInstance();
        int year=Integer.parseInt(month.substring(0,4));
        int mon =Integer.parseInt(month.substring(5,7))-1;
        calendar.set(year, mon, 1);
        calendar.roll(Calendar.DATE, -1);

        String startTime = month + " 00:00:00";
        String endTime = simp.format(calendar.getTime()) + " 23:59:59";
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, clientAppName,startTime,endTime,
                        TransactionReportType.MONTHLY);

        ClientCallTransactionTypeStatisticDataMerger merger = new ClientCallTransactionTypeStatisticDataMerger(clientAppName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }


    /**
     * 获取最近一小时的TransactionType调用次数的结果集,不进行分页
     *
     * @param group               系统类别
     * @param clientAppName       消费系统名称
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机总和
     * @return 调用次数结果集, 返回对象中durations的总长度为60, Key值为0-59,表示一小时从第0分钟到第59分钟的每分钟调用次数
     */
    @Override
    public TransactionCallTimesReport queryLastHourTransactionTypeCallTimesReportByClient(String group,
                                                                                          String clientAppName,
                                                                                          String serverAppName,
                                                                                          String transactionTypeName,
                                                                                          String serverIpAddress) {
            Map<String, Object> map = new HashMap<>();
            map.put("domain", serverAppName);
            map.put("startTime", this.getCurrentHour());
            map.put("transactionType", transactionTypeName);

            if (StringUtils.hasText(serverIpAddress))
                map.put("serverIp", serverIpAddress);

            //获取当前一小时的实时数据
            List<TransactionReportVO> reports =
                    this.transactionDataStorage.queryRealtimeTransactionReports(group, map);

        ClientCallTransactionTypeStatisticDataMerger merger = new ClientCallTransactionTypeStatisticDataMerger(clientAppName);

            for (TransactionReportVO report : reports)
                merger.add(report);

            TransactionCallTimesReport transactionCallTimesReport = new TransactionCallTimesReport();
            LinkedHashMap<Integer, Long> durations = new LinkedHashMap<>();
            LinkedHashMap<Integer, Range2> range2sMap = merger.getRange2s();
            transactionCallTimesReport.setDurations(durations);

            for (int i = 0; i < 60; i++) {
                Range2 range2 = range2sMap.get(i);
                if (range2 == null)
                    durations.put(i, 0L);
                else
                    durations.put(i, (long) range2.getCount());
            }

            return transactionCallTimesReport;
    }

    /**
     * 获取当天的TransactionType调用次数的结果集,不进行分页
     *
     * @param group               系统类别
     * @param clientAppName       消费系统名称
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称

     * @return 调用次数结果集, 返回对象中durations的总长度为24, Key值为0-23,表示一天从0点到23点的每小时调用次数
     **/
    @Override
    public TransactionCallTimesReport queryTodayTransactionTypeCallTimesReportByClient(String group,
                                                                                       String clientAppName,
                                                                                       String serverAppName,
                                                                                       String transactionTypeName) {
        Map<String, Object> map = new HashMap<>();
        map.put("transactionType", transactionTypeName);
        if (StringUtils.hasText(clientAppName))
            map.put("clientAppName", clientAppName);

        //获取当前一小时的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryRealtimeTransactionReports(group, serverAppName,
                        this.getToday(), this.getCurrentHour(), map);

        LinkedHashMap<Integer, Long> durations = new LinkedHashMap<>();
        for (int i = 0; i < 24; i++)
            durations.put(i, 0L);

        for (TransactionReportVO report : reports) {
            int hour = Integer.parseInt(report.getStartTime().substring(11, 13));

            for (TransactionMachineVO machine : report.getMachines()) {
                for (TransactionClientVO client : machine.getTransactionClients()) {
                    if (StringUtils.isEmpty(client.getDomain()) || !clientAppName.equals(client.getDomain()))
                        continue;

                    for (TransactionTypeVO transactionType : client.getTransactionTypes()) {
                        if (StringUtils.hasText(transactionTypeName) &&
                                !transactionType.getName().equals(transactionTypeName))
                            continue;

                        Long count = durations.get(hour);
                        durations.put(hour, transactionType.getTotalCount() + count);
                    }
                }
            }
        }

        TransactionCallTimesReport report = new TransactionCallTimesReport();
        report.setDurations(durations);

        return report;
    }


    /**
     * 获取指定一小时的TransactionType调用次数的结果集,不进行分页
     *
     * @param group               系统类别
     * @param clientAppName       消费系统名称
     * @param serverAppName       应用服务系统名称
     * @param hour                指定小时,格式为 yyyy-MM-dd HH:mm:ss
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机总和
     * @return 调用次数结果集, 返回对象中durations的总长度为60, Key值为0-59,表示一小时从第0分钟到第59分钟的每分钟调用次数
     */
    @Override
    public TransactionCallTimesReport queryHourTransactionTypeCallTimesReportByClient(String group,
                                                                                      String clientAppName,
                                                                                      String serverAppName,
                                                                                      String hour,
                                                                                      String transactionTypeName,
                                                                                      String serverIpAddress) {
        String startTime = hour.replace(hour.substring(14,19),"00:00");
        String endTime = hour.replace(hour.substring(14,19),"59:59");


        Map<String, Object> map = new HashMap<>();
        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        //获取当前一小时的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName, startTime, endTime, TransactionReportType.HOURLY, map);

        ClientCallTransactionTypeStatisticDataMerger merger = new ClientCallTransactionTypeStatisticDataMerger(clientAppName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionCallTimesReport transactionCallTimesReport = new TransactionCallTimesReport();
        LinkedHashMap<Integer, Long> durations = new LinkedHashMap<>();
        LinkedHashMap<Integer, Range2> range2sMap = merger.getRange2s();
        transactionCallTimesReport.setDurations(durations);

        for (int i = 0; i < 60; i++) {
            Range2 range2 = range2sMap.get(i);
            if (range2 == null)
                durations.put(i, 0L);
            else
                durations.put(i, (long) range2.getCount());
        }

        return transactionCallTimesReport;
    }


    /**
     * 获取指定天的TransactionType调用次数的结果集,不进行分页
     *
     * @param group               系统类别
     * @param clientAppName       消费系统名称
     * @param serverAppName       应用服务系统名称
     * @param date                指定日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称

     * @return 调用次数结果集, 返回对象中durations的总长度为60, Key值为0-59,表示一小时从第0分钟到第59分钟的每分钟调用次数
     */
    @Override
    public TransactionCallTimesReport queryDayTransactionTypeCallTimesReportByClient(String group,
                                                                                     String clientAppName,
                                                                                     String serverAppName,
                                                                                     String date,
                                                                                     String transactionTypeName) {
        String startTime = date  +  " " + "00:00:00";
        String endTime = date  +  " " + "23:59:59";

        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(clientAppName))
            map.put("clientAppName", clientAppName);

        //获取当前一小时的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName, startTime, endTime,
                        TransactionReportType.DAILY, map );

        ClientCallTransactionTypeStatisticDataMerger merger = new ClientCallTransactionTypeStatisticDataMerger(clientAppName);


        for (TransactionReportVO report : reports)
            merger.add(report);


        LinkedHashMap<Integer, Long> durations = new LinkedHashMap<>();


        for (int i = 0; i < 24; i++)
            durations.put(i, 0L);

        for (TransactionReportVO report : reports) {
            int hour = Integer.parseInt(report.getStartTime().substring(11, 13));

            for (TransactionMachineVO machine : report.getMachines()) {
                for (TransactionClientVO client : machine.getTransactionClients()) {
                    if (StringUtils.isEmpty(client.getDomain()) || !clientAppName.equals(client.getDomain()))
                        continue;

                    for (TransactionTypeVO transactionType : client.getTransactionTypes()) {
                        if (StringUtils.hasText(transactionTypeName) &&
                                !transactionType.getName().equals(transactionTypeName))
                            continue;

                        Long count = durations.get(hour);
                        durations.put(hour, transactionType.getTotalCount() + count);
                    }
                }
            }
        }

        TransactionCallTimesReport report = new TransactionCallTimesReport();
        report.setDurations(durations);

        return report;
    }

    /**
     * 获取指定周的TransactionName服务步骤统计结果不进行分页
     *
     * @param group               系统类别
     * @param clientAppName       消费系统名称
     * @param serverAppName       应用服务系统名称
     * @param week                指定周的第一天日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称

     * @return 统计数据结果集
     */
    @Override
    public TransactionCallTimesReport queryWeekTransactionTypeCallTimesReportByClient(String group,
                                                                                      String clientAppName,
                                                                                      String serverAppName,
                                                                                      String week,
                                                                                      String transactionTypeName) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,Integer.parseInt(week.substring(0,4)));
        cal.set(Calendar.MONTH,Integer.parseInt(week.substring(5,7))-1);
        cal.set(Calendar.DATE,Integer.parseInt(week.substring(8,10)));
        int day = cal.get(Calendar.DAY_OF_WEEK)-1;
        cal.add(Calendar.DATE,6);

        String startTime = week + " 00:00:00";
        String endTime = simp.format(cal.getTime()) + " 23:59:59";

        Map<String, Object> map = new HashMap<>();

        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(clientAppName))
            map.put("clientAppName", clientAppName);

        //获取当前一小时的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName, startTime, endTime,
                        TransactionReportType.WEEKLY, map );

        ClientCallTransactionTypeStatisticDataMerger merger = new ClientCallTransactionTypeStatisticDataMerger(clientAppName);



        for (TransactionReportVO report : reports)
            merger.add(report);


        LinkedHashMap<Integer, Long> durations = new LinkedHashMap<>();


        for (int i = 1; i < 8; i++)
            durations.put(i, 0L);

        for (TransactionReportVO report : reports) {

                if (StringUtils.hasText(serverAppName) &&
                        !report.getDomain().equals(serverAppName))
                    continue;
            for (TransactionMachineVO machine : report.getMachines()) {
                for (TransactionClientVO client : machine.getTransactionClients()) {
                    for (TransactionTypeVO transactionType : client.getTransactionTypes()) {
                        if (StringUtils.hasText(transactionTypeName) &&
                                !transactionType.getName().equals(transactionTypeName))
                            continue;

                        Long count = durations.get(day);
                        durations.put(day, transactionType.getTotalCount() + count);
                    }
                }
            }
        }
        TransactionCallTimesReport report = new TransactionCallTimesReport();
        report.setDurations(durations);
        return report;
    }

    /**
     * 获取指定月的TransactionType调用次数的结果集,不进行分页
     *
     * @param group               系统类别
     * @param clientAppName       消费系统名称
     * @param serverAppName       应用服务系统名称
     * @param month               指定月份的第一条日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称

     * @return 调用次数结果集, 返回对象中durations的总长度为60, Key值为0-59,表示一小时从第0分钟到第59分钟的每分钟调用次数
     */
    @Override
    public TransactionCallTimesReport queryMonthTransactionTypeCallTimesReportByClient(String group,
                                                                                       String clientAppName,
                                                                                       String serverAppName,
                                                                                       String month,
                                                                                       String transactionTypeName) {
        Calendar calendar = Calendar.getInstance();
        int year=Integer.parseInt(month.substring(0,4));
        int mon =Integer.parseInt(month.substring(5,7))-1;
        int day = Integer.parseInt(month.substring(8,10));
        calendar.set(year, mon, 1);
        calendar.roll(Calendar.DATE, -1);
        int maxDate  =  calendar.getActualMaximum(Calendar.DATE);

        String startTime = month + " 00:00:00";
        String endTime = simp.format(calendar.getTime()) + " 23:59:59";

        Map<String, Object> map = new HashMap<>();

        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(clientAppName))
            map.put("clientAppName", clientAppName);

        //获取当前一小时的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName, startTime, endTime,
                        TransactionReportType.MONTHLY, map );

        ClientCallTransactionTypeStatisticDataMerger merger = new ClientCallTransactionTypeStatisticDataMerger(clientAppName);


        for (TransactionReportVO report : reports)
            merger.add(report);


        LinkedHashMap<Integer, Long> durations = new LinkedHashMap<>();


        for (int i = 1; i <= maxDate; i++)
            durations.put(i, 0L);

        for (TransactionReportVO report : reports) {

                if (StringUtils.hasText(clientAppName) &&
                        !report.getDomain().equals(clientAppName))
                    continue;
            for (TransactionMachineVO machine : report.getMachines()) {
                for (TransactionClientVO client : machine.getTransactionClients()) {
                    for (TransactionTypeVO transactionType : client.getTransactionTypes()) {
                        if (StringUtils.hasText(transactionTypeName) &&
                                !transactionType.getName().equals(transactionTypeName))
                            continue;

                        Long count = durations.get(day);
                        durations.put(day, transactionType.getTotalCount() + count);
                    }
                }
            }
        }
        TransactionCallTimesReport report = new TransactionCallTimesReport();
        report.setDurations(durations);
        return report;
    }

    private String getCurrentHour() {
        long timestamp = System.currentTimeMillis();
        timestamp = timestamp - timestamp % HOUR;
        Date thisHour = new Date(timestamp);
        return this.simpleDateFormat.format(thisHour);
    }


    private String getToday() {
        long timestamp = System.currentTimeMillis();
        timestamp = timestamp - timestamp % DAY;
        Date today = new Date(timestamp);
        return this.simpleDateFormat.format(today);
    }
}
