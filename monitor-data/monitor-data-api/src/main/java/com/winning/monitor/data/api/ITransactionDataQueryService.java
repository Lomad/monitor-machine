package com.winning.monitor.data.api;

import com.winning.monitor.data.api.transaction.domain.*;


import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Created by nicholasyan on 16/10/20.
 */
public interface ITransactionDataQueryService {

    /**
     * 获取所有的应用服务系统名称
     *
     * @param group               系统类别
     * @return
     */
    LinkedHashSet<String> getAllServerAppNames(String group);

    /**
     * 获取今天和昨天的调用数
     */
     ServerCount getTodayAndYestodaySize();

    /**
     * 获取今天和昨天错误的调用数
     */
     ServerCount getTodayAndYestodayWrongSize();

    /**
     * 根据客户端类型获取客户端调用数
     */
     ServerCountWithType getTodaySizeByClientType();

    /**
     * 根据系统获取系统总调用数，正常调用数，异常调用数
     */
    ServerCountWithDomainList getTodaySizeByDomain();

    /**
     * 获取错误调用的明细
     */
     WrongMessageList getLastHourWrongMessageList();


    /**
     * 获取所有的应用服务系统对应的IP地址
     *
     * @param group               系统类别
     * @return
     */
    LinkedHashSet<String> getAllServerIpAddress(String group, String domain);

    /**
     * 获取最近一小时的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName 应用服务系统名称
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryLastHourTransactionTypeReportByServer(String group,String serverAppName);


    /**
     * 获取当天的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName 应用服务系统名称
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryTodayTransactionTypeReportByServer(String group, String serverAppName);


    /**
     * 获取指定小时的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName 应用服务系统名称
     * @param hour          指定小时,格式为 yyyy-MM-dd HH:mm:ss
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryHourTransactionTypeReportByServer(String group,
                                                                        String serverAppName,
                                                                        String hour);

    /**
     * 获取指定日期的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName 应用服务系统名称
     * @param date          指定日期,格式为 yyyy-MM-dd
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryDayTransactionTypeReportByServer(String group,
                                                                     String serverAppName,
                                                                     String date);


    /**
     * 获取指定周的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName 应用服务系统名称
     * @param week          指定周的第一天日期,格式为 yyyy-MM-dd
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryWeekTransactionTypeReportByServer(String group,
                                                                      String serverAppName,
                                                                      String week);


    /**
     * 获取指定月的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName 应用服务系统名称
     * @param month         指定月份的第一条日期,格式为 yyyy-MM-dd
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryMonthTransactionTypeReportByServer(String group,
                                                                       String serverAppName,
                                                                       String month);


    /**
     * 获取最近一小时的TransactionName服务步骤统计结果不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryLastHourTransactionNameReportByServer(String group,
                                                                          String serverAppName,
                                                                          String transactionTypeName,
                                                                          String serverIpAddress,
                                                                          String clientAppName);

    /**
     * 获取当天的TransactionName服务步骤统计结果不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryTodayTransactionNameReportByServer(String group,
                                                                       String serverAppName,
                                                                          String transactionTypeName,
                                                                          String serverIpAddress,
                                                                          String clientAppName);

    /**
     * 获取指定天的TransactionName服务步骤统计结果不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param date          指定日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryDayTransactionNameReportByServer(String group,
                                                                     String serverAppName,
                                                                         String date,
                                                                         String transactionTypeName,
                                                                         String serverIpAddress,
                                                                        String clientAppName);

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
     TransactionStatisticReport queryWeekTransactionNameReportByServer(String group,
                                                                       String serverAppName,
                                                                             String week,
                                                                             String transactionTypeName,
                                                                             String serverIpAddress,
                                                                             String clientAppName);


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
     TransactionCallTimesReport queryMonthTransactionTypeCallTimesReportByServer(String group,
                                                                                 String serverAppName,
                                                                                       String month,
                                                                                       String transactionTypeName,
                                                                                       String serverIpAddress);


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
    TransactionStatisticReport queryMonthTransactionNameReportByServer(String group,
                                                                       String serverAppName,
                                                                              String month,
                                                                              String transactionTypeName,
                                                                              String serverIpAddress,
                                                                              String clientAppName);


    /**
     * 获取指定小时的TransactionName服务步骤统计结果不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param hour                指定小时,格式为 yyyy-MM-dd HH:mm:ss
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryHourTransactionNameReportByServer(String group,
                                                                      String serverAppName,
                                                                          String hour,
                                                                          String transactionTypeName,
                                                                          String serverIpAddress,
                                                                          String clientAppName);


    /**
     * 获取最近一小时的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryLastHourTransactionTypeReportByClient(String group,
                                                                          String serverAppName,
                                                                          String transactionTypeName,
                                                                          String serverIpAddress);

    /**
     * 获取指定小时的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param hour          指定小时,格式为 yyyy-MM-dd HH:mm:ss
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryHourTransactionTypeReportByClient(String group,
                                                                      String serverAppName,
                                                                          String hour,
                                                                          String transactionTypeName,
                                                                          String serverIpAddress);

       /**
     * 获取最近一小时的TransactionType调用次数的结果集,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机总和
     * @return 调用次数结果集, 返回对象中durations的总长度为60, Key值为0-59,表示一小时从第0分钟到第59分钟的每分钟调用次数
     */
    TransactionCallTimesReport queryLastHourTransactionTypeCallTimesReportByServer(String group,
                                                                                   String serverAppName,
                                                                                   String transactionTypeName,
                                                                                   String serverIpAddress);

    /**
     * 获取最近一小时的TransactionType调用次数的结果集,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param hour                指定小时,格式为 yyyy-MM-dd HH:mm:ss
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机总和
     * @return 调用次数结果集, 返回对象中durations的总长度为60, Key值为0-59,表示一小时从第0分钟到第59分钟的每分钟调用次数
     */
    TransactionCallTimesReport queryHourTransactionTypeCallTimesReportByServer(String group,
                                                                               String serverAppName,
                                                                                   String hour,
                                                                                   String transactionTypeName,
                                                                                   String serverIpAddress);


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

     TransactionCallTimesReport queryDayTransactionTypeCallTimesReportByServer(String group,
                                                                               String serverAppName,
                                                                                     String date,
                                                                                     String transactionTypeName,
                                                                                     String serverIpAddress);



    /**
     * 获取当天的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryTodayTransactionTypeReportByClient(String group,
                                                                       String serverAppName,
                                                                       String transactionTypeName,
                                                                       String serverIpAddress);

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
    TransactionCallTimesReport queryWeekTransactionTypeCallTimesReportByServer(String group,
                                                                               String serverAppName,
                                                                                      String week,
                                                                                      String transactionTypeName,
                                                                                      String serverIpAddress);


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
     TransactionStatisticReport queryDayTransactionTypeReportByClient(String group,
                                                                      String serverAppName,
                                                                            String date,
                                                                            String transactionTypeName,
                                                                            String serverIpAddress);

    /**
     * 获取指定周的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param week          指定周的第一天日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
    TransactionStatisticReport queryWeekTransactionTypeReportByClient(String group,
                                                                      String serverAppName,
                                                                             String week,
                                                                             String transactionTypeName,
                                                                             String serverIpAddress);

    /**
     * 获取指定周的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param month         指定月份的第一条日期,格式为 yyyy-MM-dd
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机
     * @return 统计数据结果集
     */
     TransactionStatisticReport queryMonthTransactionTypeReportByClient(String group,
                                                                        String serverAppName,
                                                                              String month,
                                                                              String transactionTypeName,
                                                                              String serverIpAddress);


    /**
     * 获取最近一小时的TransactionType调用次数的结果集,不进行分页
     *
     * @param group               系统类别
     * @param serverAppName       应用服务系统名称
     * @param transactionTypeName 服务大类名称
     * @param serverIpAddress     应用服务端的IP地址,如果传空,表示所有主机总和
     * @return 调用次数结果集, 返回对象中durations的总长度为24, Key值为0-23,表示一天从0点到23点的每小时调用次数
     */
    TransactionCallTimesReport queryTodayTransactionTypeCallTimesReportByServer(String group,
                                                                                String serverAppName,
                                                                                String transactionTypeName,
                                                                                String serverIpAddress);


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
TransactionMessageList queryLastHourTransactionMessageList(String group,
                                                           String serverAppName,
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
     * @param group               系统类别
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
    TransactionMessageList queryHourTransactionMessageList(String group,
                                                           String serverAppName,
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
     * @param group               系统类别
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
    TransactionMessageList queryTodayTransactionMessageList(String group,
                                                            String serverAppName,
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
    TransactionMessageList queryDayTransactionMessageList(String group,
                                                          String serverAppName,
                                                          String date,
                                                          String transactionTypeName,
                                                          String transactionName,
                                                          String serverIpAddress,
                                                          String clientAppName,
                                                          String clientIpAddress,
                                                          String status,
                                                          int startIndex, int pageSize,
                                                          LinkedHashMap<String, String> orderBy);

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
   TransactionMessageList queryWeekTransactionMessageList(String group,
                                                          String serverAppName,
                                                                  String week,
                                                                  String transactionTypeName,
                                                                  String transactionName,
                                                                  String serverIpAddress,
                                                                  String clientAppName,
                                                                  String clientIpAddress,
                                                                  String status,
                                                                  int startIndex, int pageSize,
                                                                  LinkedHashMap<String, String> orderBy);

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
     TransactionMessageList queryMonthTransactionMessageList(String group,
                                                             String serverAppName,
                                                                   String month,
                                                                   String transactionTypeName,
                                                                   String transactionName,
                                                                   String serverIpAddress,
                                                                   String clientAppName,
                                                                   String clientIpAddress,
                                                                   String status,
                                                                   int startIndex, int pageSize,
                                                                   LinkedHashMap<String, String> orderBy);



    /**
     * 获取指定月内的调用消息明细记录
     *  @param group               系统类别
     * @param messageId           记录Id
     * @param  index                详情的位置,index=-1,表示服务的详情。
     * @param serverAppName       应用服务系统名称,非空
     */
    TransactionMessageListDetail queryTransactionMessageListDetails(String group,
                                                                    String messageId,
                                                                    int index,
                                                                    String serverAppName) ;

}
