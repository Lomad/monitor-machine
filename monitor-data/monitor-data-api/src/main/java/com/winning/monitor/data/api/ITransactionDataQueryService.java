package com.winning.monitor.data.api;

import com.winning.monitor.data.api.transaction.domain.TransactionCallTimesReport;
import com.winning.monitor.data.api.transaction.domain.TransactionStatisticReport;

import java.util.LinkedHashSet;

/**
 * Created by nicholasyan on 16/10/20.
 */
public interface ITransactionDataQueryService {

    /**
     * 获取所有的应用服务系统名称
     *
     * @return
     */
    LinkedHashSet<String> getAllServerAppNames();

    /**
     * 获取最近一小时的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param serverAppName 应用服务系统名称
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryLastHourTransactionTypeReportByServer(String serverAppName);


    /**
     * 获取最近一小时的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     *
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryLastHourTransactionTypeReportByClient(String serverAppName,
                                                                          String transactionTypeName,
                                                                          String serverIpAddress);

    /**
     * 获取最近一小时的TransactionType调用次数的结果集,不进行分页
     *
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机总和
     * @return 调用次数结果集, 返回对象中durations的总长度为60, Key值为0-59,表示一小时从第0分钟到第59分钟的每分钟调用次数
     */
    TransactionCallTimesReport queryLastHourTransactionTypeCallTimesReportByServer(String serverAppName,
                                                                                   String transactionTypeName,
                                                                                   String serverIpAddress);



}
