package com.winning.monitor.data.api;

import com.winning.monitor.data.api.transaction.domain.TransactionCallTimesReport;
import com.winning.monitor.data.api.transaction.domain.TransactionMessageList;
import com.winning.monitor.data.api.transaction.domain.TransactionStatisticReport;

import java.text.ParseException;
import java.util.LinkedHashMap;
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
     * 获取所有的应用服务系统对应的IP地址
     *
     * @return
     */
    LinkedHashSet<String> getAllServerIpAddress(String domain);

    /**
     * 获取最近一小时的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param serverAppName 应用服务系统名称
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryLastHourTransactionTypeReportByServer(String serverAppName);


    /**
     * 获取当天的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param serverAppName 应用服务系统名称
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryTodayTransactionTypeReportByServer(String serverAppName);


    /**
     * 获取指定小时的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param serverAppName 应用服务系统名称
     * @param hour          指定小时,格式为 yyyy-MM-dd HH:mm:ss
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryHourTransactionTypeReportByServer(String serverAppName,
                                                                      String hour);

    /**
     * 获取指定日期的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param serverAppName 应用服务系统名称
     * @param date          指定日期,格式为 yyyy-MM-dd
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryDayTransactionTypeReportByServer(String serverAppName,
                                                                     String date);


    /**
     * 获取指定周的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param serverAppName 应用服务系统名称
     * @param week          指定周的第一天日期,格式为 yyyy-MM-dd
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryWeekTransactionTypeReportByServer(String serverAppName,
                                                                      String week);


    /**
     * 获取指定月的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param serverAppName 应用服务系统名称
     * @param month         指定月份的第一条日期,格式为 yyyy-MM-dd
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryMonthTransactionTypeReportByServer(String serverAppName,
                                                                       String month);


    /**
     * 获取最近一小时的TransactionName服务步骤统计结果不进行分页
     *
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryLastHourTransactionNameReportByServer(String serverAppName,
                                                                          String transactionTypeName,
                                                                          String serverIpAddress);

    /**
     * 获取当天的TransactionName服务步骤统计结果不进行分页
     *
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryTodayTransactionNameReportByServer(String serverAppName,
                                                                          String transactionTypeName,
                                                                          String serverIpAddress);


    /**
     * 获取指定小时的TransactionName服务步骤统计结果不进行分页
     *
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param hour                指定小时,格式为 yyyy-MM-dd HH:mm:ss
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryHourTransactionNameReportByServer(String serverAppName,
                                                                          String hour,
                                                                          String transactionTypeName,
                                                                          String serverIpAddress);


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
     * 获取指定小时的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     *
     * @param serverAppName       应用服务系统名称
     * @param hour          指定小时,格式为 yyyy-MM-dd HH:mm:ss
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryHourTransactionTypeReportByClient(String serverAppName,
                                                                          String hour,
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

    /**
     * 获取最近一小时的TransactionType调用次数的结果集,不进行分页
     *
     * @param serverAppName       应用服务系统名称
     * @param hour                指定小时,格式为 yyyy-MM-dd HH:mm:ss
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机总和
     * @return 调用次数结果集, 返回对象中durations的总长度为60, Key值为0-59,表示一小时从第0分钟到第59分钟的每分钟调用次数
     */
    TransactionCallTimesReport queryHourTransactionTypeCallTimesReportByServer(String serverAppName,
                                                                                   String hour,
                                                                                   String transactionTypeName,
                                                                                   String serverIpAddress);



    /**
     * 获取当天的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     *
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryTodayTransactionTypeReportByClient(String serverAppName,
                                                                       String transactionTypeName,
                                                                       String serverIpAddress);


    /**
     * 获取最近一小时的TransactionType调用次数的结果集,不进行分页
     *
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机总和
     * @return 调用次数结果集, 返回对象中durations的总长度为24, Key值为0-23,表示一天从0点到23点的每小时调用次数
     */
    TransactionCallTimesReport queryTodayTransactionTypeCallTimesReportByServer(String serverAppName,
                                                                                String transactionTypeName,
                                                                                String serverIpAddress);


    /**
 * 获取最近一小时内的调用消息明细记录
 *
 * @param serverAppName       应用服务系统名称,非空
 * @param transactionTypeName 服务大类名称,非空
 * @param transactionName     服务名称,可选
 * @param serverIpAddress     服务端系统IP地址,可选,不填表示所有服务端主机
 * @param clientAppName       客户端系统名称,可选,不填表示所有客户端系统
 * @param clientIpAddress     客户端系统IP地址,可选,不填表示所有客户端主机
 * @param status              过滤消息状态,可选,可填成功或失败,不填表示所有状态记录,可传入"成功"或"失败"
 * @param startIndex          分页起始位置,非空
 * @param pageSize            分页每页的条数,非空
 * @param orderBy             排序参数, key表示需要排序的字段,value表示排序顺序,DESC或ASC,且要按照顺序,不填则不进行排序
 *                            可传入的Key:
 *                            time      根据时间排序
 *                            duration  根据用时排序
 *                            status    根据状态排序
 * @return 详细调用Transaction的明细清单
 */
TransactionMessageList queryLastHourTransactionMessageList(String serverAppName,
                                                           String transactionTypeName,
                                                           String transactionName,
                                                           String serverIpAddress,
                                                           String clientAppName,
                                                           String clientIpAddress,
                                                           String status,
                                                           int startIndex,
                                                           int pageSize,
                                                           LinkedHashMap<String, String> orderBy);

    /**
     * 获取指定小时内的调用消息明细记录
     *
     * @param serverAppName       应用服务系统名称,非空
     * @param hour                指定小时,格式为 yyyy-MM-dd HH:mm:ss
     * @param transactionTypeName 服务大类名称,非空
     * @param transactionName     服务名称,可选
     * @param serverIpAddress     服务端系统IP地址,可选,不填表示所有服务端主机
     * @param clientAppName       客户端系统名称,可选,不填表示所有客户端系统
     * @param clientIpAddress     客户端系统IP地址,可选,不填表示所有客户端主机
     * @param status              过滤消息状态,可选,可填成功或失败,不填表示所有状态记录,可传入"成功"或"失败"
     * @param startIndex          分页起始位置,非空
     * @param pageSize            分页每页的条数,非空
     * @param orderBy             排序参数, key表示需要排序的字段,value表示排序顺序,DESC或ASC,且要按照顺序,不填则不进行排序
     *                            可传入的Key:
     *                            time      根据时间排序
     *                            duration  根据用时排序
     *                            status    根据状态排序
     * @return 详细调用Transaction的明细清单
     */
    TransactionMessageList queryHourTransactionMessageList(String serverAppName,
                                                               String hour,
                                                               String transactionTypeName,
                                                               String transactionName,
                                                               String serverIpAddress,
                                                               String clientAppName,
                                                               String clientIpAddress,
                                                               String status,
                                                               int startIndex,
                                                               int pageSize,
                                                               LinkedHashMap<String, String> orderBy);




    /**
     * 获取当天内的调用消息明细记录
     *
     * @param serverAppName       应用服务系统名称,非空
     * @param transactionTypeName 服务大类名称,非空
     * @param transactionName     服务名称,可选
     * @param serverIpAddress     服务端系统IP地址,可选,不填表示所有服务端主机
     * @param clientAppName       客户端系统名称,可选,不填表示所有客户端系统
     * @param clientIpAddress     客户端系统IP地址,可选,不填表示所有客户端主机
     * @param status              过滤消息状态,可选,可填成功或失败,不填表示所有状态记录,可传入"成功"或"失败"
     * @param startIndex          分页起始位置,非空
     * @param pageSize            分页每页的条数,非空
     * @param orderBy             排序参数, key表示需要排序的字段,value表示排序顺序,DESC或ASC,且要按照顺序,不填则不进行排序
     *                            可传入的Key:
     *                            time      根据时间排序
     *                            duration  根据用时排序
     *                            status    根据状态排序
     * @return 详细调用Transaction的明细清单
     */
    TransactionMessageList queryTodayTransactionMessageList(String serverAppName,
                                                               String transactionTypeName,
                                                               String transactionName,
                                                               String serverIpAddress,
                                                               String clientAppName,
                                                               String clientIpAddress,
                                                               String status,
                                                               int startIndex,
                                                               int pageSize,
                                                               LinkedHashMap<String, String> orderBy);


}
