package com.winning.monitor.data.transaction;

import com.winning.monitor.agent.logging.message.LogMessage;
import com.winning.monitor.agent.logging.message.MessageTree;
import com.winning.monitor.agent.logging.transaction.DefaultTransaction;
import com.winning.monitor.data.api.ITransactionDataQueryService;
import com.winning.monitor.data.api.transaction.domain.*;
import com.winning.monitor.data.api.transaction.domain.ServerCount;
import com.winning.monitor.data.api.transaction.vo.*;
import com.winning.monitor.data.api.vo.Range2;
import com.winning.monitor.data.storage.api.ITransactionDataStorage;
import com.winning.monitor.data.storage.api.MessageTreeStorage;
import com.winning.monitor.data.storage.api.entity.MessageTreeList;
import com.winning.monitor.data.transaction.builder.TransactionCallTimesMerger;
import com.winning.monitor.data.transaction.builder.TransactionNameServerStatisticDataMerger;
import com.winning.monitor.data.transaction.builder.TransactionStatisticDataClientMerger;
import com.winning.monitor.data.transaction.builder.TransactionStatisticDataMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by nicholasyan on 16/10/20.
 */
@Service
public class TransactionDataQueryService implements ITransactionDataQueryService {

    private static final long HOUR = 3600 * 1000L;
    private static final long DAY = HOUR * 24;


    @Autowired
    private ITransactionDataStorage transactionDataStorage;

    @Autowired
    private MessageTreeStorage messageTreeStorage;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取所有的应用服务系统名称
     *
     * @param group               系统类别
     * @return
     */
    @Override
    public LinkedHashSet<String> getAllServerAppNames(String group) {
        return transactionDataStorage.findAllTransactionDomains(group);
    }

    /**
     * 获取今天和昨天的调用数
     */
    @Override
    public ServerCount getTodayAndYestodaySize() {
        Map<String, Object> map = new HashMap<>();
        long todayCount = this.transactionDataStorage.findAllserverSize(this.getToday(),
                this.getCurrentHour(),map).getTotalSum();

        long yesdayCount =this.transactionDataStorage.findAllserverSize(this.getYestoday(),
                this.getToday(),map).getTotalSum();
        ServerCount serverCount = new ServerCount();
        serverCount.setTodayCount(todayCount);
        serverCount.setYestodayCount(yesdayCount);
        return serverCount;

    }

    @Override
    public ServerCount getTodayAndYestodayWrongSize() {

        Map<String, Object> map = new HashMap<>();

        long todayWrongCount = this.transactionDataStorage.findAllserverSize(this.getToday(),
                this.getCurrentHour(),map).getFailSum();

        long yesdayWrongCount =this.transactionDataStorage.findAllserverSize(this.getYestoday(),
                this.getToday(),map).getFailSum();
        ServerCount serverCount = new ServerCount();
        serverCount.setTodayCount(todayWrongCount);
        serverCount.setYestodayCount(yesdayWrongCount);
        return serverCount;

    }

    @Override
    public ServerCountWithType getTodaySizeByClientType() {

        Map<String, Object> map = new HashMap<>();
        LinkedHashMap<String, Long> serverCount = new LinkedHashMap();
        ServerCountWithType serverCountWithType = new ServerCountWithType();
        List clientType = this.transactionDataStorage.findAllTransactionTypes();
        for(int i=0;i<clientType.size();i++) {
            map.put("clientType", clientType.get(i));
            Long todayCount = this.transactionDataStorage.findAllserverSize(this.getToday(),
                    this.getCurrentHour(), map).getTotalSum();
            serverCount.put(clientType.get(i).toString(),todayCount);

        }
        serverCountWithType.setServerCountMap(serverCount);
        return  serverCountWithType;

    }

    @Override
    public ServerCountWithDomainList getTodaySizeByDomain() {

        ServerCountWithDomainList serverCountWithDomainList = new ServerCountWithDomainList();
        LinkedHashSet<String> domains = this.transactionDataStorage.findAllTransactionDomains("BI");
        List list = new ArrayList(domains);
        for(Object i:list){

            ServerCountWithDomain serverCountWithDomain = this.toServerCountWithDomain(i);
            serverCountWithDomainList.addCountMessage(serverCountWithDomain);
        }
        return serverCountWithDomainList;
    }

    public ServerCountWithDomain toServerCountWithDomain(Object i){
        Map<String, Object> map = new HashMap<>();
        ServerCountWithDomain serverCountWithDomain = new ServerCountWithDomain();
        map.put("status",1);
        map.put("severAppName",i);
        Long todayWrongCount = this.transactionDataStorage.findAllserverSize(this.getToday(),
                this.getCurrentHour(), map).getFailSum();
        map.put("status",0);
        Long todayCount = this.transactionDataStorage.findAllserverSize(this.getToday(),
                this.getCurrentHour(), map).getTotalSum();
        Long todayRightCount = todayCount - todayWrongCount;
        serverCountWithDomain.setServerAppName(i.toString());
        serverCountWithDomain.setTodayCount(todayCount);
        serverCountWithDomain.setTodayRightCount(todayRightCount);
        serverCountWithDomain.setTodayWrongCount(todayWrongCount);

        return serverCountWithDomain;
    }

    @Override
    public WrongMessageList getLastHourWrongMessageList() {

        Map<String, Object> map = this.getArgumentMap(null , null,
                null, null, null, "失败");
        WrongMessageList wrongMessageList = new WrongMessageList();
        long start = System.currentTimeMillis();
        start = start - start % HOUR;
        long end = start + HOUR;
        LinkedHashMap<String, String> order = new LinkedHashMap<>();
        order.put("time", "ASC");
        LinkedHashMap<String, String> tips = new LinkedHashMap<>();
        tips.put("error","服务调用失败");
        LinkedHashSet<String> domains = this.transactionDataStorage.findAllTransactionDomains("BI");
        for(String domain:domains) {
            MessageTreeList messageList = this.messageTreeStorage.queryMessageTree("BI", domain, start, end, map, 1, 1, order);
            for (MessageTree messageTree : messageList.getMessageTrees()) {
                DefaultTransaction transaction = (DefaultTransaction) messageTree.getMessage();
                wrongMessageList.setDomain(messageTree.getDomain());
                wrongMessageList.setServerIpAddress(messageTree.getIpAddress());
                wrongMessageList.setTransactionTypeName(transaction.getType());
                wrongMessageList.setCurrentTime(this.simpleDateFormat.format(new Date(transaction.getTimestamp())));
                wrongMessageList.setTips(tips);
            }
        }
        return wrongMessageList;
    }


    /**
     * 获取所有的应用服务系统对应的IP地址
     *
     * @param group               系统类别
     * @param domain
     * @return
     */
    @Override
    public LinkedHashSet<String> getAllServerIpAddress(String group, String domain) {
        return transactionDataStorage.findAllServerIpAddress(group, domain);
    }

    /**
     * 获取最近一小时的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName 应用服务系统名称
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryLastHourTransactionTypeReportByServer(String group, String serverAppName) {

        //获取当前一小时的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryRealtimeTransactionReports(group, serverAppName, this.getCurrentHour());

        TransactionStatisticDataMerger merger = new TransactionStatisticDataMerger(serverAppName,
                TransactionStatisticDataMerger.TransactionLevel.TransactionType,
                TransactionStatisticDataMerger.StatisticGroupType.Server);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }

    /**
     * 获取最近一小时的TransactionName服务步骤统计结果不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryLastHourTransactionNameReportByServer(String group,
                                                                                 String serverAppName,
                                                                                 String transactionTypeName,
                                                                                 String serverIpAddress,
                                                                                 String clientAppName) {
        Map<String, Object> map = new HashMap<>();
        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        if (StringUtils.hasText(clientAppName))
            map.put("clientAppName", clientAppName);

        //获取指定时间的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryRealtimeTransactionReports(group, serverAppName, this.getCurrentHour(),
                        this.getCurrentHour(), map);

        TransactionNameServerStatisticDataMerger merger = new TransactionNameServerStatisticDataMerger(serverAppName,clientAppName,
                serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }

    /**
     * 获取指定小时的TransactionName服务步骤统计结果不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param hour                指定小时,格式为 yyyy-MM-dd HH:mm:ss
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryHourTransactionNameReportByServer(String group,
                                                                             String serverAppName,
                                                                                 String hour,
                                                                                 String transactionTypeName,
                                                                                 String serverIpAddress,
                                                                                 String clientAppName) {
        Map<String, Object> map = new HashMap<>();
        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        if (StringUtils.hasText(clientAppName))
            map.put("clientAppName", clientAppName);


        String startTime = hour.replace(hour.substring(14,19),"00:00");
        String endTime = hour.substring(0,14)+"59:59";

        //获取指定时间的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group,
                        serverAppName, startTime,
                        endTime, TransactionReportType.HOURLY,map);

        TransactionNameServerStatisticDataMerger merger = new TransactionNameServerStatisticDataMerger(serverAppName,clientAppName,
                serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }


    /**
     * 获取当天的TransactionName服务步骤统计结果不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryTodayTransactionNameReportByServer(String group,
                                                                              String serverAppName,
                                                                                 String transactionTypeName,
                                                                                 String serverIpAddress,
                                                                                 String clientAppName) {
        Map<String, Object> map = new HashMap<>();
        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        if (StringUtils.hasText(clientAppName))
            map.put("clientAppName", clientAppName);

        //获取指定时间的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryRealtimeTransactionReports(group, serverAppName, this.getToday(),
                        this.getCurrentHour(), map);

        TransactionNameServerStatisticDataMerger merger = new TransactionNameServerStatisticDataMerger(serverAppName,clientAppName,
                serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }

    /**
     * 获取指定天的TransactionName服务步骤统计结果不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param date                指定日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryDayTransactionNameReportByServer(String group,
                                                                            String serverAppName,
                                                                                 String date,
                                                                                 String transactionTypeName,
                                                                                 String serverIpAddress,
                                                                                 String clientAppName) {
        String startTime = date  +  " " + "00:00:00";
        String endTime = date  +  " " + "23:59:59";

        Map<String, Object> map = new HashMap<>();
        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        if (StringUtils.hasText(clientAppName))
            map.put("clientAppName", clientAppName);

        //获取指定时间的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName,startTime ,endTime,
                        TransactionReportType.DAILY, map);

        TransactionNameServerStatisticDataMerger merger = new TransactionNameServerStatisticDataMerger(serverAppName,clientAppName,
                serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }


    /**
     * 获取指定周的TransactionName服务步骤统计结果不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param week                指定周的第一天日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryWeekTransactionNameReportByServer(String group,
                                                                             String serverAppName,
                                                                            String week,
                                                                            String transactionTypeName,
                                                                            String serverIpAddress,
                                                                             String clientAppName) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,Integer.parseInt(week.substring(0,4)));
        cal.set(Calendar.MONTH,Integer.parseInt(week.substring(5,7))-1);
        cal.set(Calendar.DATE,Integer.parseInt(week.substring(8,10)));
        cal.add(Calendar.DATE,6);

        String startTime = week + " 00:00:00";
        String endTime = simp.format(cal.getTime()) + " 23:59:59";

        Map<String, Object> map = new HashMap<>();
        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        if (StringUtils.hasText(clientAppName))
            map.put("clientAppName", clientAppName);

        //获取指定时间的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName,startTime ,endTime,
                        TransactionReportType.WEEKLY, map);

        TransactionNameServerStatisticDataMerger merger = new TransactionNameServerStatisticDataMerger(serverAppName,clientAppName,
                serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }

    /**
     * 获取指定月的TransactionName服务步骤统计结果不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param month               指定月份的第一条日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryMonthTransactionNameReportByServer(String group,
                                                                              String serverAppName,
                                                                             String month,
                                                                             String transactionTypeName,
                                                                             String serverIpAddress,
                                                                              String clientAppName) {
        Calendar calendar = Calendar.getInstance();
        int year=Integer.parseInt(month.substring(0,4));
        int mon =Integer.parseInt(month.substring(5,7))-1;
        calendar.set(year, mon, 1);
        calendar.roll(Calendar.DATE, -1);

        String startTime = month + " 00:00:00";
        String endTime = simp.format(calendar.getTime()) + " 23:59:59";

        Map<String, Object> map = new HashMap<>();
        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        if (StringUtils.hasText(clientAppName))
            map.put("clientAppName", clientAppName);

        //获取指定时间的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName,startTime ,endTime,
                        TransactionReportType.MONTHLY, map);

        TransactionNameServerStatisticDataMerger merger = new TransactionNameServerStatisticDataMerger(serverAppName,clientAppName,
                serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }


    /**
     * 获取当天的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName 应用服务系统名称
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryTodayTransactionTypeReportByServer(String group,
                                                                              String serverAppName) {

        //获取当天的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryRealtimeTransactionReports(group, serverAppName,
                        this.getToday(), this.getCurrentHour());

        TransactionStatisticDataMerger merger = new TransactionStatisticDataMerger(serverAppName,
                TransactionStatisticDataMerger.TransactionLevel.TransactionType,
                TransactionStatisticDataMerger.StatisticGroupType.Server);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }

    /**
     * 获取指定小时的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName 应用服务系统名称
     * @param hour          指定小时,格式为 yyyy-MM-dd HH:mm:ss
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryHourTransactionTypeReportByServer(String group,
                                                                             String serverAppName,
                                                                             String hour) {
        // 需要从Mongodb的TransactionHourlyReports中获取
        // TransactionHourlyReports格式和TransactionRealtimeReports格式一样

        String startTime = hour.replace(hour.substring(14,19),"00:00");
        String endTime = hour.substring(0,14)+"59:59";

        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName,startTime,endTime,
                        TransactionReportType.HOURLY);

        TransactionStatisticDataMerger merger = new TransactionStatisticDataMerger(serverAppName,
                TransactionStatisticDataMerger.TransactionLevel.TransactionType,
                TransactionStatisticDataMerger.StatisticGroupType.Server);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;

    }

    /**
     * 获取指定日期的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName 应用服务系统名称
     * @param date          指定日期,格式为 yyyy-MM-dd
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryDayTransactionTypeReportByServer(String group,
                                                                            String serverAppName,
                                                                            String date) {

        String startTime = date  +  " " + "00:00:00";
        String endTime = date  +  " " + "23:59:59";

        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName,startTime,endTime,
                        TransactionReportType.DAILY);

        TransactionStatisticDataMerger merger = new TransactionStatisticDataMerger(serverAppName,
                TransactionStatisticDataMerger.TransactionLevel.TransactionType,
                TransactionStatisticDataMerger.StatisticGroupType.Server);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;

    }

    /**
     * 获取指定周的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName 应用服务系统名称
     * @param week          指定周的第一天日期,格式为 yyyy-MM-dd
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryWeekTransactionTypeReportByServer(String group,
                                                                             String serverAppName,
                                                                             String week) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,Integer.parseInt(week.substring(0,4)));
        cal.set(Calendar.MONTH,Integer.parseInt(week.substring(5,7))-1);
        cal.set(Calendar.DATE,Integer.parseInt(week.substring(8,10)));
        cal.add(Calendar.DATE,6);

        String startTime = week + " 00:00:00";
        String endTime = simp.format(cal.getTime()) + " 23:59:59";
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName,startTime,endTime,
                        TransactionReportType.WEEKLY);

        TransactionStatisticDataMerger merger = new TransactionStatisticDataMerger(serverAppName,
                TransactionStatisticDataMerger.TransactionLevel.TransactionType,
                TransactionStatisticDataMerger.StatisticGroupType.Server);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;

}

    /**
     * 获取指定月的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName 应用服务系统名称
     * @param month         指定月份的第一条日期,格式为 yyyy-MM-dd
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryMonthTransactionTypeReportByServer(String group,
                                                                              String serverAppName,
                                                                              String month) {
        Calendar calendar = Calendar.getInstance();
        int year=Integer.parseInt(month.substring(0,4));
        int mon =Integer.parseInt(month.substring(5,7))-1;
        calendar.set(year, mon, 1);
        calendar.roll(Calendar.DATE, -1);

        String startTime = month + " 00:00:00";
        String endTime = simp.format(calendar.getTime()) + " 23:59:59";
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName,startTime,endTime,
                        TransactionReportType.MONTHLY);

        TransactionStatisticDataMerger merger = new TransactionStatisticDataMerger(serverAppName,
                TransactionStatisticDataMerger.TransactionLevel.TransactionType,
                TransactionStatisticDataMerger.StatisticGroupType.Server);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;

    }

    /**
     * 获取最近一小时的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryLastHourTransactionTypeReportByClient(String group,
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

        TransactionStatisticDataClientMerger merger = new TransactionStatisticDataClientMerger(serverAppName,
                TransactionStatisticDataClientMerger.TransactionLevel.TransactionType,
                serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }


    /**
     * 获取指定小时的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param hour                指定小时,格式为 yyyy-MM-dd HH:mm:ss
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryHourTransactionTypeReportByClient(String group,
                                                                             String serverAppName,
                                                                                 String hour,
                                                                                 String transactionTypeName,
                                                                                 String serverIpAddress){
        String startTime = hour.replace(hour.substring(14,19),"00:00");
        String endTime = hour.substring(0,14)+"59:59";

        Map<String, Object> map = new HashMap<>();

        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        //获取当前一小时的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName, startTime, endTime, TransactionReportType.HOURLY, map);

        TransactionStatisticDataClientMerger merger = new TransactionStatisticDataClientMerger(serverAppName,
                TransactionStatisticDataClientMerger.TransactionLevel.TransactionType,
                serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }

    /**
     * 获取当天的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryTodayTransactionTypeReportByClient(String group,
                                                                              String serverAppName,
                                                                              String transactionTypeName,
                                                                              String serverIpAddress) {
        Map<String, Object> map = new HashMap<>();
        map.put("transactionType", transactionTypeName);
        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        //获取指定时间的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryRealtimeTransactionReports(group, serverAppName,
                        this.getToday(), this.getCurrentHour(), map);

        TransactionStatisticDataClientMerger merger = new TransactionStatisticDataClientMerger(serverAppName,
                TransactionStatisticDataClientMerger.TransactionLevel.TransactionType,
                serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }

    /**
     * 获取指定日期的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param date                指定日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryDayTransactionTypeReportByClient(String group,
                                                                            String serverAppName,
                                                                            String date,
                                                                            String transactionTypeName,
                                                                            String serverIpAddress) {

        String startTime = date  +  " " + "00:00:00";
        String endTime = date  +  " " + "23:59:59";

        Map<String, Object> map = new HashMap<>();
        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        //获取当前一小时的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName, startTime, endTime,
                        TransactionReportType.DAILY, map);

        TransactionStatisticDataClientMerger merger = new TransactionStatisticDataClientMerger(serverAppName,
                TransactionStatisticDataClientMerger.TransactionLevel.TransactionType,
                serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }


    /**
     * 获取指定周的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param week                指定周的第一天日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryWeekTransactionTypeReportByClient(String group,
                                                                             String serverAppName,
                                                                            String week,
                                                                            String transactionTypeName,
                                                                            String serverIpAddress) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,Integer.parseInt(week.substring(0,4)));
        cal.set(Calendar.MONTH,Integer.parseInt(week.substring(5,7))-1);
        cal.set(Calendar.DATE,Integer.parseInt(week.substring(8,10)));
        cal.add(Calendar.DATE,6);

        String startTime = week + " 00:00:00";
        String endTime = simp.format(cal.getTime()) + " 23:59:59";

        Map<String, Object> map = new HashMap<>();
        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        //获取当前一小时的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName, startTime, endTime,
                        TransactionReportType.WEEKLY, map);

        TransactionStatisticDataClientMerger merger = new TransactionStatisticDataClientMerger(serverAppName,
                TransactionStatisticDataClientMerger.TransactionLevel.TransactionType,
                serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }


    /**
     * 获取指定周的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param month               指定月份的第一条日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryMonthTransactionTypeReportByClient(String group,
                                                                              String serverAppName,
                                                                             String month,
                                                                             String transactionTypeName,
                                                                             String serverIpAddress) {

        Calendar calendar = Calendar.getInstance();
        int year=Integer.parseInt(month.substring(0,4));
        int mon =Integer.parseInt(month.substring(5,7))-1;
        calendar.set(year, mon, 1);
        calendar.roll(Calendar.DATE, -1);

        String startTime = month + " 00:00:00";
        String endTime = simp.format(calendar.getTime()) + " 23:59:59";

        Map<String, Object> map = new HashMap<>();
        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        //获取当前一小时的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName, startTime, endTime,
                        TransactionReportType.MONTHLY, map);

        TransactionStatisticDataClientMerger merger = new TransactionStatisticDataClientMerger(serverAppName,
                TransactionStatisticDataClientMerger.TransactionLevel.TransactionType,
                serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }


    /**
     * 获取最近一小时的TransactionType调用次数的结果集,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机总和
     * @return 调用次数结果集, 返回对象中durations的总长度为60, Key值为0-59,表示一小时从第0分钟到第59分钟的每分钟调用次数
     */
    @Override
    public TransactionCallTimesReport queryLastHourTransactionTypeCallTimesReportByServer(String group,
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

        TransactionCallTimesMerger transactionCallTimesMerger =
                new TransactionCallTimesMerger(serverAppName, serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            transactionCallTimesMerger.add(report);

        TransactionCallTimesReport transactionCallTimesReport = new TransactionCallTimesReport();
        LinkedHashMap<Integer, Long> durations = new LinkedHashMap<>();
        LinkedHashMap<Integer, Range2> range2sMap = transactionCallTimesMerger.getRange2s();
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
     * 获取指定小时的TransactionType调用次数的结果集,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param hour                指定小时,格式为 yyyy-MM-dd HH:mm:ss
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机总和
     * @return 调用次数结果集, 返回对象中durations的总长度为60, Key值为0-59,表示一小时从第0分钟到第59分钟的每分钟调用次数
     */
    @Override
    public TransactionCallTimesReport queryHourTransactionTypeCallTimesReportByServer(String group,
                                                                                      String serverAppName,
                                                                                          String hour,
                                                                                          String transactionTypeName,
                                                                                          String serverIpAddress) {


            String startTime = hour.replace(hour.substring(14,19),"00:00");
            String endTime = hour.substring(0,14)+"59:59";


        Map<String, Object> map = new HashMap<>();
        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        //获取当前一小时的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName, startTime, endTime, TransactionReportType.HOURLY, map);

        TransactionCallTimesMerger transactionCallTimesMerger =
                new TransactionCallTimesMerger(serverAppName, serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            transactionCallTimesMerger.add(report);

        TransactionCallTimesReport transactionCallTimesReport = new TransactionCallTimesReport();
        LinkedHashMap<Integer, Long> durations = new LinkedHashMap<>();
        LinkedHashMap<Integer, Range2> range2sMap = transactionCallTimesMerger.getRange2s();
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
         * @param serverAppName       应用服务系统名称
         * @param transactionTypeName 服务大类名称
         * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机总和
         * @return 调用次数结果集, 返回对象中durations的总长度为24, Key值为0-23,表示一天从0点到23点的每小时调用次数
         */
        @Override
        public TransactionCallTimesReport queryTodayTransactionTypeCallTimesReportByServer(String group,
                                                                                           String serverAppName,
                                                                                                String transactionTypeName,
                                                                                                String serverIpAddress) {
            Map<String, Object> map = new HashMap<>();
            map.put("transactionType", transactionTypeName);
            if (StringUtils.hasText(serverIpAddress))
                map.put("serverIp", serverIpAddress);

            //获取当前一小时的实时数据
            List<TransactionReportVO> reports =
                    this.transactionDataStorage.queryRealtimeTransactionReports(group, serverAppName,
                            this.getToday(), this.getCurrentHour(), map);

            TransactionCallTimesMerger merger = new TransactionCallTimesMerger(serverAppName, serverIpAddress, transactionTypeName);


            for (TransactionReportVO report : reports)
                merger.add(report);

            LinkedHashMap<Integer, Long> durations = new LinkedHashMap<>();
            for (int i = 0; i < 24; i++)
                durations.put(i, 0L);

            for (TransactionReportVO report : reports) {
                int hour = Integer.parseInt(report.getStartTime().substring(11, 13));
                for (TransactionMachineVO machine : report.getMachines()) {
                    if (StringUtils.hasText(serverIpAddress) &&
                            !machine.getIp().equals(serverIpAddress))
                        continue;
                    for (TransactionClientVO client : machine.getTransactionClients()) {
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
     * 获取最近一小时的TransactionType调用次数的结果集,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param date                指定日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机总和
     * @return 调用次数结果集, 返回对象中durations的总长度为60, Key值为0-59,表示一小时从第0分钟到第59分钟的每分钟调用次数
     */
    @Override
    public TransactionCallTimesReport queryDayTransactionTypeCallTimesReportByServer(String group,
                                                                                     String serverAppName,
                                                                                          String date,
                                                                                          String transactionTypeName,
                                                                                          String serverIpAddress) {
        String startTime = date  +  " " + "00:00:00";
        String endTime = date  +  " " + "23:59:59";

        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        //获取当前一小时的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName, startTime, endTime,
                        TransactionReportType.HOURLY, map );

        TransactionCallTimesMerger transactionCallTimesMerger =
                new TransactionCallTimesMerger(serverAppName, serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            transactionCallTimesMerger.add(report);


        LinkedHashMap<Integer, Long> durations = new LinkedHashMap<>();


        for (int i = 0; i < 24; i++)
            durations.put(i, 0L);

        for (TransactionReportVO report : reports) {
            int hour = Integer.parseInt(report.getStartTime().substring(11, 13));
            for (TransactionMachineVO machine : report.getMachines()) {
                if (StringUtils.hasText(serverIpAddress) &&
                        !machine.getIp().equals(serverIpAddress))
                    continue;
                for (TransactionClientVO client : machine.getTransactionClients()) {
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
     * 获取指定周的TransactionType调用次数的结果集,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param week                指定周的第一天日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机总和
     * @return 调用次数结果集, 返回对象中durations的总长度为60, Key值为0-59,表示一小时从第0分钟到第59分钟的每分钟调用次数
     */
    @Override
    public TransactionCallTimesReport queryWeekTransactionTypeCallTimesReportByServer(String group,
                                                                                      String serverAppName,
                                                                                     String week,
                                                                                     String transactionTypeName,
                                                                                     String serverIpAddress) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,Integer.parseInt(week.substring(0,4)));
        cal.set(Calendar.MONTH,Integer.parseInt(week.substring(5,7))-1);
        cal.set(Calendar.DATE,Integer.parseInt(week.substring(8,10)));

        cal.add(Calendar.DATE,6);

        String startTime = week + " 00:00:00";
        String endTime = simp.format(cal.getTime()) + " 23:59:59";

        Map<String, Object> map = new HashMap<>();

        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        //获取当前一小时的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName, startTime, endTime,
                        TransactionReportType.DAILY, map );

        TransactionCallTimesMerger transactionCallTimesMerger =
                new TransactionCallTimesMerger(serverAppName, serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            transactionCallTimesMerger.add(report);


        LinkedHashMap<Integer, Long> durations = new LinkedHashMap<>();


        for (int i = 1; i < 8; i++)
            durations.put(i, 0L);

            for (TransactionReportVO report : reports) {
                Calendar calendar = Calendar.getInstance();
                boolean isFirstSunday = (calendar.getFirstDayOfWeek() == Calendar.SUNDAY);
                calendar.set(Calendar.YEAR,Integer.parseInt(report.getStartTime().substring(0,4)));
                calendar.set(Calendar.MONTH,Integer.parseInt(report.getStartTime().substring(5,7))-1);
                calendar.set(Calendar.DATE,Integer.parseInt(report.getStartTime().substring(8,10)));
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                if(isFirstSunday){
                    day = day - 1;
                    if(day == 0){
                        day = 7;
                    }
                }
                for (TransactionMachineVO machine : report.getMachines()) {
                    if (StringUtils.hasText(serverIpAddress) &&
                            !machine.getIp().equals(serverIpAddress))
                        continue;
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
     * @param serverAppName       应用服务系统名称
     * @param month               指定月份的第一条日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机总和
     * @return 调用次数结果集, 返回对象中durations的总长度为60, Key值为0-59,表示一小时从第0分钟到第59分钟的每分钟调用次数
     */
    @Override
    public TransactionCallTimesReport queryMonthTransactionTypeCallTimesReportByServer(String group,
                                                                                       String serverAppName,
                                                                                      String month,
                                                                                      String transactionTypeName,
                                                                                      String serverIpAddress) {
        Calendar calendar = Calendar.getInstance();
        int year=Integer.parseInt(month.substring(0,4));
        int mon =Integer.parseInt(month.substring(5,7))-1;
        calendar.set(year, mon, 1);
        calendar.roll(Calendar.DATE, -1);
        int maxDate  =  calendar.getActualMaximum(Calendar.DATE);

        String startTime = month + " 00:00:00";
        String endTime = simp.format(calendar.getTime()) + " 23:59:59";

        Map<String, Object> map = new HashMap<>();

        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        //获取当前一小时的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryHistoryTransactionReports(group, serverAppName, startTime, endTime,
                        TransactionReportType.DAILY, map );

        TransactionCallTimesMerger transactionCallTimesMerger =
                new TransactionCallTimesMerger(serverAppName, serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            transactionCallTimesMerger.add(report);


        LinkedHashMap<Integer, Long> durations = new LinkedHashMap<>();


        for (int i = 1; i <= maxDate; i++)
            durations.put(i, 0L);

        for (TransactionReportVO report : reports) {
            int day = Integer.parseInt(report.getStartTime().substring(8,10));
            for (TransactionMachineVO machine : report.getMachines()) {
                if (StringUtils.hasText(serverIpAddress) &&
                        !machine.getIp().equals(serverIpAddress))
                    continue;
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
     * 获取最近一小时内的调用消息明细记录
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称,非空
     * @param transactionTypeName 服务大类名称,非空
     * @param transactionName     服务名称,可选
     * @param serverIpAddress     服务端系统IP地址,可选,不填表示所有服务端主机
     * @param clientAppName       客户端系统名称,可选,不填表示所有客户端系统
     * @param clientIpAddress     客户端系统IP地址,可选,不填表示所有客户端主机
     * @param status              过滤消息状态,可选,可填成功或失败,不填表示所有状态记录
     * @param startIndex          分页起始位置,非空
     * @param pageSize            分页每页的条数,非空
     * @param orderBy             排序参数, key表示需要排序的字段,value表示排序顺序,DESC或ASC,且要按照顺序,不填则不进行排序
     * @return 详细调用Transaction的明细清单
     */
    @Override
    public TransactionMessageList queryLastHourTransactionMessageList(String group,
                                                                      String serverAppName,
                                                                      String transactionTypeName,
                                                                      String transactionName,
                                                                      String serverIpAddress,
                                                                      String clientAppName,
                                                                      String clientIpAddress,
                                                                      String status,
                                                                      int startIndex, int pageSize,
                                                                      LinkedHashMap<String, String> orderBy) {

        Map<String, Object> map = this.getArgumentMap(transactionTypeName, transactionName,
                serverIpAddress, clientAppName, clientIpAddress, status);

        long start = System.currentTimeMillis();
        start = start - start % HOUR;
        long end = start + HOUR;

        MessageTreeList messageList = this.messageTreeStorage.queryMessageTree(group, serverAppName, start, end, map,
                startIndex, pageSize, orderBy);

        TransactionMessageList transactionMessageList = new TransactionMessageList();
        transactionMessageList.setTotalSize(messageList.getTotalSize());

        for (MessageTree messageTree : messageList.getMessageTrees()) {
            TransactionMessage transactionMessage = this.toTransactionMessage(messageTree);
            transactionMessageList.addTransactionMessage(transactionMessage);
        }

        return transactionMessageList;
    }


    /**
     * 获取指定小时内的调用消息明细记录
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称,非空
     * @param hour                指定小时,格式为 yyyy-MM-dd HH:mm:ss
     * @param transactionTypeName 服务大类名称,非空
     * @param transactionName     服务名称,可选
     * @param serverIpAddress     服务端系统IP地址,可选,不填表示所有服务端主机
     * @param clientAppName       客户端系统名称,可选,不填表示所有客户端系统
     * @param clientIpAddress     客户端系统IP地址,可选,不填表示所有客户端主机
     * @param status              过滤消息状态,可选,可填成功或失败,不填表示所有状态记录
     * @param startIndex          分页起始位置,非空
     * @param pageSize            分页每页的条数,非空
     * @param orderBy             排序参数, key表示需要排序的字段,value表示排序顺序,DESC或ASC,且要按照顺序,不填则不进行排序
     * @return 详细调用Transaction的明细清单
     */
    @Override
    public TransactionMessageList queryHourTransactionMessageList(String group,
                                                                  String serverAppName,
                                                                      String hour,
                                                                      String transactionTypeName,
                                                                      String transactionName,
                                                                      String serverIpAddress,
                                                                      String clientAppName,
                                                                      String clientIpAddress,
                                                                      String status,
                                                                      int startIndex, int pageSize,
                                                                      LinkedHashMap<String, String> orderBy) {

        Map<String, Object> map = this.getArgumentMap(transactionTypeName, transactionName,
                serverIpAddress, clientAppName, clientIpAddress, status);
        long startDate = 0;
        long endDate = 0;
        try {
            startDate = simpleDateFormat.parse(hour.replace(hour.substring(14,19),"00:00")).getTime();
            endDate = startDate + HOUR;

        } catch (ParseException e) {
            throw new RuntimeException();
        }


        MessageTreeList messageList = this.messageTreeStorage.queryMessageTree(group, serverAppName, startDate, endDate, map,
                startIndex, pageSize, orderBy);

        TransactionMessageList transactionMessageList = new TransactionMessageList();
        transactionMessageList.setTotalSize(messageList.getTotalSize());

        for (MessageTree messageTree : messageList.getMessageTrees()) {
            TransactionMessage transactionMessage = this.toTransactionMessage(messageTree);
            transactionMessageList.addTransactionMessage(transactionMessage);
        }

        return transactionMessageList;
    }

    /**
     * 获取当天的调用消息明细记录
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称,非空
     * @param transactionTypeName 服务大类名称,非空
     * @param transactionName     服务名称,可选
     * @param serverIpAddress     服务端系统IP地址,可选,不填表示所有服务端主机
     * @param clientAppName       客户端系统名称,可选,不填表示所有客户端系统
     * @param clientIpAddress     客户端系统IP地址,可选,不填表示所有客户端主机
     * @param status              过滤消息状态,可选,可填成功或失败,不填表示所有状态记录
     * @param startIndex          分页起始位置,非空
     * @param pageSize            分页每页的条数,非空
     * @param orderBy             排序参数, key表示需要排序的字段,value表示排序顺序,DESC或ASC,且要按照顺序,不填则不进行排序
     * @return 详细调用Transaction的明细清单
     */
    @Override
    public TransactionMessageList queryTodayTransactionMessageList(String group,
                                                                   String serverAppName,
                                                                      String transactionTypeName,
                                                                      String transactionName,
                                                                      String serverIpAddress,
                                                                      String clientAppName,
                                                                      String clientIpAddress,
                                                                      String status,
                                                                      int startIndex, int pageSize,
                                                                      LinkedHashMap<String, String> orderBy) {

        Map<String, Object> map = this.getArgumentMap(transactionTypeName, transactionName,
                serverIpAddress, clientAppName, clientIpAddress, status);

        long start = System.currentTimeMillis();
        start = start - start % DAY;
        long end = start + DAY;

        MessageTreeList messageList = this.messageTreeStorage.queryMessageTree(group, serverAppName, start, end, map,
                startIndex, pageSize, orderBy);

        TransactionMessageList transactionMessageList = new TransactionMessageList();
        transactionMessageList.setTotalSize(messageList.getTotalSize());

        for (MessageTree messageTree : messageList.getMessageTrees()) {
            TransactionMessage transactionMessage = this.toTransactionMessage(messageTree);
            transactionMessageList.addTransactionMessage(transactionMessage);
        }

        return transactionMessageList;
    }



    /**
     * 获取指定日期内的调用消息明细记录
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称,非空
     * @param date                指定日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称,非空
     * @param transactionName     服务名称,可选
     * @param serverIpAddress     服务端系统IP地址,可选,不填表示所有服务端主机
     * @param clientAppName       客户端系统名称,可选,不填表示所有客户端系统
     * @param clientIpAddress     客户端系统IP地址,可选,不填表示所有客户端主机
     * @param status              过滤消息状态,可选,可填成功或失败,不填表示所有状态记录
     * @param startIndex          分页起始位置,非空
     * @param pageSize            分页每页的条数,非空
     * @param orderBy             排序参数, key表示需要排序的字段,value表示排序顺序,DESC或ASC,且要按照顺序,不填则不进行排序
     * @return 详细调用Transaction的明细清单
     */
    @Override
     public TransactionMessageList queryDayTransactionMessageList(String group,
                                                                  String serverAppName,
                                                                      String date,
                                                                      String transactionTypeName,
                                                                      String transactionName,
                                                                      String serverIpAddress,
                                                                      String clientAppName,
                                                                      String clientIpAddress,
                                                                      String status,
                                                                      int startIndex, int pageSize,
                                                                      LinkedHashMap<String, String> orderBy) {

        Map<String, Object> map = this.getArgumentMap(transactionTypeName, transactionName,
                serverIpAddress, clientAppName, clientIpAddress, status);

        long startDate = 0;
        long endDate = 0;
        try {
            startDate = simpleDateFormat.parse(date  +  " " + "00:00:00").getTime();
            endDate = startDate + DAY;

        } catch (ParseException e) {
            throw new RuntimeException();
        }


        MessageTreeList messageList = this.messageTreeStorage.queryMessageTree(group, serverAppName, startDate, endDate, map,
                startIndex, pageSize, orderBy);

        TransactionMessageList transactionMessageList = new TransactionMessageList();
        transactionMessageList.setTotalSize(messageList.getTotalSize());

        for (MessageTree messageTree : messageList.getMessageTrees()) {
            TransactionMessage transactionMessage = this.toTransactionMessage(messageTree);
            transactionMessageList.addTransactionMessage(transactionMessage);
        }

        return transactionMessageList;
    }

    /**
     * 获取指定周内的调用消息明细记录
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称,非空
     * @param week                指定周的第一天日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称,非空
     * @param transactionName     服务名称,可选
     * @param serverIpAddress     服务端系统IP地址,可选,不填表示所有服务端主机
     * @param clientAppName       客户端系统名称,可选,不填表示所有客户端系统
     * @param clientIpAddress     客户端系统IP地址,可选,不填表示所有客户端主机
     * @param status              过滤消息状态,可选,可填成功或失败,不填表示所有状态记录
     * @param startIndex          分页起始位置,非空
     * @param pageSize            分页每页的条数,非空
     * @param orderBy             排序参数, key表示需要排序的字段,value表示排序顺序,DESC或ASC,且要按照顺序,不填则不进行排序
     * @return 详细调用Transaction的明细清单
     */
    @Override
    public TransactionMessageList queryWeekTransactionMessageList(String group,
                                                                  String serverAppName,
                                                                 String week,
                                                                 String transactionTypeName,
                                                                 String transactionName,
                                                                 String serverIpAddress,
                                                                 String clientAppName,
                                                                 String clientIpAddress,
                                                                 String status,
                                                                 int startIndex, int pageSize,
                                                                 LinkedHashMap<String, String> orderBy) {

        Map<String, Object> map = this.getArgumentMap(transactionTypeName, transactionName,
                serverIpAddress, clientAppName, clientIpAddress, status);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,Integer.parseInt(week.substring(0,4)));
        cal.set(Calendar.MONTH,Integer.parseInt(week.substring(5,7))-1);
        cal.set(Calendar.DATE,Integer.parseInt(week.substring(8,10)));
        cal.add(Calendar.DATE,6);

        String startTime = week + " 00:00:00";
       // String endTime = simp.format(cal.getTime()) + " 23:59:59";

        long startDate = 0;
        long endDate = 0;
        try {
            startDate = simpleDateFormat.parse(startTime).getTime();
            endDate = startDate + DAY * 7;

        } catch (ParseException e) {
            throw new RuntimeException();
        }


        MessageTreeList messageList = this.messageTreeStorage.queryMessageTree(group, serverAppName, startDate, endDate, map,
                startIndex, pageSize, orderBy);

        TransactionMessageList transactionMessageList = new TransactionMessageList();
        transactionMessageList.setTotalSize(messageList.getTotalSize());

        for (MessageTree messageTree : messageList.getMessageTrees()) {
            TransactionMessage transactionMessage = this.toTransactionMessage(messageTree);
            transactionMessageList.addTransactionMessage(transactionMessage);
        }

        return transactionMessageList;
    }

    /**
     * 获取指定月内的调用消息明细记录
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称,非空
     * @param month               指定月份的第一条日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称,非空
     * @param transactionName     服务名称,可选
     * @param serverIpAddress     服务端系统IP地址,可选,不填表示所有服务端主机
     * @param clientAppName       客户端系统名称,可选,不填表示所有客户端系统
     * @param clientIpAddress     客户端系统IP地址,可选,不填表示所有客户端主机
     * @param status              过滤消息状态,可选,可填成功或失败,不填表示所有状态记录
     * @param startIndex          分页起始位置,非空
     * @param pageSize            分页每页的条数,非空
     * @param orderBy             排序参数, key表示需要排序的字段,value表示排序顺序,DESC或ASC,且要按照顺序,不填则不进行排序
     * @return 详细调用Transaction的明细清单
     */
    @Override
    public TransactionMessageList queryMonthTransactionMessageList(String group,
                                                                   String serverAppName,
                                                                  String month,
                                                                  String transactionTypeName,
                                                                  String transactionName,
                                                                  String serverIpAddress,
                                                                  String clientAppName,
                                                                  String clientIpAddress,
                                                                  String status,
                                                                  int startIndex, int pageSize,
                                                                  LinkedHashMap<String, String> orderBy) {

        Map<String, Object> map = this.getArgumentMap(transactionTypeName, transactionName,
                serverIpAddress, clientAppName, clientIpAddress, status);

        Calendar calendar = Calendar.getInstance();
        int year=Integer.parseInt(month.substring(0,4));
        int mon =Integer.parseInt(month.substring(5,7));
        calendar.set(year, mon, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        int maxDay = calendar.get(Calendar.DAY_OF_MONTH);
        String startTime = month + " 00:00:00";
       // String endTime = simp.format(calendar.getTime()) + " 23:59:59";

        long startDate = 0;
        long endDate = 0;
        try {
            startDate = simpleDateFormat.parse(startTime).getTime();
            endDate = startDate + maxDay * DAY;

        } catch (ParseException e) {
            throw new RuntimeException();
        }


        MessageTreeList messageList = this.messageTreeStorage.queryMessageTree(group, serverAppName, startDate, endDate, map,
                startIndex, pageSize, orderBy);

        TransactionMessageList transactionMessageList = new TransactionMessageList();
        transactionMessageList.setTotalSize(messageList.getTotalSize());

        for (MessageTree messageTree : messageList.getMessageTrees()) {
            TransactionMessage transactionMessage = this.toTransactionMessage(messageTree);
            transactionMessageList.addTransactionMessage(transactionMessage);
        }

        return transactionMessageList;
    }

    @Override
    public TransactionMessageListDetail queryTransactionMessageListDetails(String group,
                                                                           String messageId,
                                                                           int index,
                                                                           String serverAppName) {
        MessageTreeList  messageList = this.messageTreeStorage.queryMessageTree(group, messageId,serverAppName);

        TransactionMessageListDetail detail = new TransactionMessageListDetail();
        TransactionMessage transactionMessage = new TransactionMessage();
        if(index == -1){
            for (MessageTree messageTree : messageList.getMessageTrees()) {
                DefaultTransaction transaction = (DefaultTransaction) messageTree.getMessage();
                if (transaction.getData() !=null) {
                    for(Map.Entry<String,Object> entry : transaction.getData().entrySet()){
                        Object value = entry.getValue();
                        if(value == null){
                            transactionMessage.getDatas().put(entry.getKey(),"");
                        }else{
                            transactionMessage.getDatas().put(entry.getKey(), value.toString());
                        }
                    }
                }
                detail.setData( transactionMessage.getDatas());
            }
        }else{
            for (MessageTree messageTree : messageList.getMessageTrees()) {
                DefaultTransaction transaction = (DefaultTransaction) messageTree.getMessage();
                if (transaction.getChildren() != null) {

                        LogMessage logMessage = transaction.getChildren().get(index);
                        DefaultTransaction childTransaction = (DefaultTransaction) logMessage;
                    if (childTransaction.getData() !=null) {
                        for(Map.Entry<String,Object> entry : childTransaction.getData().entrySet()){
                            Object value = entry.getValue();
                            if(value == null){
                                transactionMessage.getDatas().put(entry.getKey(),"");
                            }else{
                                transactionMessage.getDatas().put(entry.getKey(), value.toString());
                            }
                        }
                    }
                       detail.setData(transactionMessage.getDatas());
                    }
                }
            }

        return detail;
    }

    private TransactionMessage toTransactionMessage(MessageTree messageTree) {
        DefaultTransaction transaction = (DefaultTransaction) messageTree.getMessage();
        TransactionMessage transactionMessage = this.toTransactionMessage(transaction);
        if(messageTree.getCaller()!=null) {
            transactionMessage.setClientAppName(messageTree.getCaller().getName());
            transactionMessage.setClientIpAddress(messageTree.getCaller().getIp());
        }else{
            transactionMessage.setClientAppName(null);
            transactionMessage.setClientIpAddress(null);
        }
        transactionMessage.setClientType(messageTree.getCaller().getType());
        transactionMessage.setServerAppName(messageTree.getDomain());
        transactionMessage.setServerIpAddress(messageTree.getIpAddress());
        transactionMessage.setMessageId(messageTree.getMessageId());

        return transactionMessage;
    }

    private TransactionMessage toTransactionMessage(DefaultTransaction transaction) {
        TransactionMessage transactionMessage = new TransactionMessage();
        transactionMessage.setStartTime(this.simpleDateFormat.format(new Date(transaction.getTimestamp())));
        transactionMessage.setTransactionTypeName(transaction.getType());
        transactionMessage.setTransactionName(transaction.getName());
        transactionMessage.setUseTime(transaction.getDurationInMillis());


        if (transaction.getData() !=null) {
            for(Map.Entry<String,Object> entry : transaction.getData().entrySet()){
                Object value = entry.getValue();
                if(value == null){
                    transactionMessage.getDatas().put(entry.getKey(),"");
                }else{
                    transactionMessage.getDatas().put(entry.getKey(), value.toString());
                }
            }
        }

        if ("0".equals(transaction.getStatus())) {
            transactionMessage.setStatus("成功");
        } else {
            transactionMessage.setStatus("失败");
            transactionMessage.setErrorMessage(transactionMessage.getStatus());
        }

        if (transaction.getChildren() != null) {
            for (LogMessage logMessage : transaction.getChildren()) {
                DefaultTransaction childTransaction = (DefaultTransaction) logMessage;
                transactionMessage.addTransactionMessage(this.toTransactionMessage(childTransaction));
            }
        }

        return transactionMessage;
    }


    private Map<String, Object> getArgumentMap(String transactionTypeName,
                                               String transactionName,
                                               String serverIpAddress,
                                               String clientAppName,
                                               String clientIpAddress,
                                               String status) {

        Map<String, Object> map = new HashMap<>();
        if (StringUtils.hasText(transactionTypeName))
            map.put("transactionTypeName", transactionTypeName);
        if (StringUtils.hasText(transactionName))
            map.put("transactionName", transactionName);
        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIpAddress", serverIpAddress);
        if (StringUtils.hasText(clientAppName))
            map.put("clientAppName", clientAppName);
        if (StringUtils.hasText(clientIpAddress))
            map.put("clientIpAddress", clientIpAddress);

        if ("成功".equals(status))
            map.put("status", "0");
        else if ("失败".equals(status))
            map.put("status", "-1");

        return map;
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

    private String getYestoday() {
        long timestamp = System.currentTimeMillis()-DAY;
        timestamp = timestamp - timestamp % DAY;
        Date today = new Date(timestamp);
        return this.simpleDateFormat.format(today);
    }

}
