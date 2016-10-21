package com.winning.monitor.data.transaction;

import com.winning.monitor.data.api.ITransactionDataQueryService;
import com.winning.monitor.data.api.transaction.domain.TransactionCallTimesReport;
import com.winning.monitor.data.api.transaction.domain.TransactionMessageList;
import com.winning.monitor.data.api.transaction.domain.TransactionStatisticReport;
import com.winning.monitor.data.api.transaction.vo.TransactionReportVO;
import com.winning.monitor.data.storage.api.ITransactionDataStorage;
import com.winning.monitor.data.transaction.builder.TransactionStatisticDataMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by nicholasyan on 16/10/20.
 */
@Service
public class TransactionDataQueryService implements ITransactionDataQueryService {

    private static final long HOUR = 3600 * 1000L;

    @Autowired
    private ITransactionDataStorage transactionDataStorage;
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
     * 获取最近一小时的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     *
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    @Override
    public TransactionStatisticReport queryLastHourTransactionTypeReportByClient(String serverAppName, String transactionTypeName, String serverIpAddress) {
        return null;
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
    public TransactionCallTimesReport queryLastHourTransactionTypeCallTimesReportByServer(String serverAppName, String transactionTypeName, String serverIpAddress) {
        return null;
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
    public TransactionMessageList queryLastHourTransactionMessageList(String serverAppName, String transactionTypeName, String transactionName, String serverIpAddress, String clientAppName, String clientIpAddress, String status, long startIndex, int pageSize, LinkedHashMap<String, String> orderBy) {
        return null;
    }

    private String getCurrentHour() {
        long timestamp = System.currentTimeMillis();
        timestamp = timestamp - timestamp % HOUR;
        Date thisHour = new Date(timestamp);
        return this.simpleDateFormat.format(thisHour);
    }

}
