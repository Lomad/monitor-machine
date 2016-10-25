package com.winning.monitor.data.transaction;

import com.winning.monitor.agent.logging.message.LogMessage;
import com.winning.monitor.agent.logging.message.MessageTree;
import com.winning.monitor.agent.logging.transaction.DefaultTransaction;
import com.winning.monitor.data.api.ITransactionDataQueryService;
import com.winning.monitor.data.api.transaction.domain.TransactionCallTimesReport;
import com.winning.monitor.data.api.transaction.domain.TransactionMessage;
import com.winning.monitor.data.api.transaction.domain.TransactionMessageList;
import com.winning.monitor.data.api.transaction.domain.TransactionStatisticReport;
import com.winning.monitor.data.api.transaction.vo.TransactionClientVO;
import com.winning.monitor.data.api.transaction.vo.TransactionMachineVO;
import com.winning.monitor.data.api.transaction.vo.TransactionReportVO;
import com.winning.monitor.data.api.transaction.vo.TransactionTypeVO;
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

    /**
     * 获取所有的应用服务系统名称
     *
     * @return
     */
    @Override
    public LinkedHashSet<String> getAllServerAppNames() {
        return transactionDataStorage.findAllTransactionDomains();
    }

    /**
     * 获取所有的应用服务系统对应的IP地址
     *
     * @param domain
     * @return
     */
    @Override
    public LinkedHashSet<String> getAllServerIpAddress(String domain) {
        return transactionDataStorage.findAllServerIpAddress(domain);
    }

    /**
     * 获取最近一小时的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param serverAppName 应用服务系统名称
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryLastHourTransactionTypeReportByServer(String serverAppName) {

        //获取当前一小时的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryRealtimeTransactionReports(serverAppName, this.getCurrentHour());

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
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryLastHourTransactionNameReportByServer(String serverAppName,
                                                                                 String transactionTypeName,
                                                                                 String serverIpAddress) {
        Map<String, Object> map = new HashMap<>();
        map.put("transactionType", transactionTypeName);

        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        //获取指定时间的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryRealtimeTransactionReports(serverAppName, this.getCurrentHour(),
                        this.getCurrentHour(), map);

        TransactionNameServerStatisticDataMerger merger = new TransactionNameServerStatisticDataMerger(serverAppName,
                serverIpAddress, transactionTypeName);

        for (TransactionReportVO report : reports)
            merger.add(report);

        TransactionStatisticReport report = merger.toTransactionStatisticReport();
        return report;
    }


    /**
     * 获取当天的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param serverAppName 应用服务系统名称
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryTodayTransactionTypeReportByServer(String serverAppName) {

        //获取当天的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryRealtimeTransactionReports(serverAppName,
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
     * 获取最近一小时的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     *
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryLastHourTransactionTypeReportByClient(String serverAppName,
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
                this.transactionDataStorage.queryRealtimeTransactionReports(map);

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
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryTodayTransactionTypeReportByClient(String serverAppName, String transactionTypeName, String serverIpAddress) {
        Map<String, Object> map = new HashMap<>();
        map.put("transactionType", transactionTypeName);
        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        //获取指定时间的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryRealtimeTransactionReports(serverAppName, this.getToday(), this.getCurrentHour(), map);

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
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机总和
     * @return 调用次数结果集, 返回对象中durations的总长度为60, Key值为0-59,表示一小时从第0分钟到第59分钟的每分钟调用次数
     */
    @Override
    public TransactionCallTimesReport queryLastHourTransactionTypeCallTimesReportByServer(String serverAppName,
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
                this.transactionDataStorage.queryRealtimeTransactionReports(map);

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
     * 获取最近一小时的TransactionType调用次数的结果集,不进行分页
     *
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机总和
     * @return 调用次数结果集, 返回对象中durations的总长度为24, Key值为0-23,表示一天从0点到23点的每小时调用次数
     */
    @Override
    public TransactionCallTimesReport queryTodayTransactionTypeCallTimesReportByServer(String serverAppName,
                                                                                       String transactionTypeName,
                                                                                       String serverIpAddress) {
        Map<String, Object> map = new HashMap<>();
        map.put("transactionType", transactionTypeName);
        if (StringUtils.hasText(serverIpAddress))
            map.put("serverIp", serverIpAddress);

        //获取当前一小时的实时数据
        List<TransactionReportVO> reports =
                this.transactionDataStorage.queryRealtimeTransactionReports(serverAppName,
                        this.getToday(), this.getCurrentHour(), map);

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
     * 获取最近一小时内的调用消息明细记录
     *
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
    public TransactionMessageList queryLastHourTransactionMessageList(String serverAppName,
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

        MessageTreeList messageList = this.messageTreeStorage.queryMessageTree(serverAppName, start, end, map,
                startIndex, pageSize, orderBy);

        TransactionMessageList transactionMessageList = new TransactionMessageList();
        transactionMessageList.setTotalSize(messageList.getTotalSize());

        for (MessageTree messageTree : messageList.getMessageTrees()) {
            TransactionMessage transactionMessage = this.toTransactionMessage(messageTree);
            transactionMessageList.addTransactionMessage(transactionMessage);
        }

        return transactionMessageList;
    }

    private TransactionMessage toTransactionMessage(MessageTree messageTree) {
        DefaultTransaction transaction = (DefaultTransaction) messageTree.getMessage();
        TransactionMessage transactionMessage = this.toTransactionMessage(transaction);

        transactionMessage.setClientAppName(messageTree.getCaller().getName());
        transactionMessage.setClientIpAddress(messageTree.getCaller().getIp());
        transactionMessage.setClientType(messageTree.getCaller().getType());
        transactionMessage.setServerAppName(messageTree.getDomain());
        transactionMessage.setServerIpAddress(messageTree.getIpAddress());

        return transactionMessage;
    }

    private TransactionMessage toTransactionMessage(DefaultTransaction transaction) {
        TransactionMessage transactionMessage = new TransactionMessage();
        transactionMessage.setStartTime(this.simpleDateFormat.format(new Date(transaction.getTimestamp())));
        transactionMessage.setTransactionTypeName(transaction.getType());
        transactionMessage.setTransactionName(transaction.getName());
        transactionMessage.setUseTime(transaction.getDurationInMillis());

        if (StringUtils.hasText(transaction.getData())) {
            String datas = String.valueOf(transaction.getData());
            String[] keyValues = datas.split("&");
            for (String keyValue : keyValues) {
                String[] kv = keyValue.split("=");
                if (kv.length >= 2) {
                    transactionMessage.getDatas().put(kv[0], kv[1]);
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

}
