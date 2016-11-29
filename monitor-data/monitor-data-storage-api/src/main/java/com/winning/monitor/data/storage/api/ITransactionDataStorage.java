package com.winning.monitor.data.storage.api;

import com.winning.monitor.data.api.transaction.vo.SumVO;
import com.winning.monitor.data.api.transaction.vo.TransactionReportType;
import com.winning.monitor.data.api.transaction.vo.TransactionReportVO;
import com.winning.monitor.data.storage.api.exception.StorageException;
import com.winning.monitor.data.api.transaction.vo.UsersVO;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by nicholasyan on 16/9/14.
 */
public interface ITransactionDataStorage {

    List<UsersVO> findUsers(String username, String password);

    SumVO findAllserverSize(String startTime, String endTime, Map<String, Object> map);


    List<String> findAllTransactionTypes();

    LinkedHashSet<String> findAllTransactionDomains(String group);

    LinkedHashSet<String> findAllTransactionClients(String group);

    LinkedHashSet<String> findAllServerIpAddress(String group, String domain);

    List<TransactionReportVO> queryRealtimeTransactionReports(String group, String domain, String startTime);

    List<TransactionReportVO> queryRealtimeClientTransactionReports(String group, String domain, String startTime);

    List<TransactionReportVO> queryRealtimeTransactionReports(String group, String domain, String startTime, String endTime);

    List<TransactionReportVO> queryRealtimeClientTransactionReports(String group, String domain, String startTime, String endTime);

    List<TransactionReportVO> queryRealtimeTransactionReports(String group, Map<String, Object> map);

    List<TransactionReportVO> queryRealtimeClientTransactionReports(String group, Map<String, Object> map);

    List<TransactionReportVO> queryRealtimeTransactionReports(String group,String domain, String startTime, String endTime, Map<String, Object> map);

    List<TransactionReportVO> queryRealtimeClientTransactionReports(String group,String domain, String startTime, String endTime, Map<String, Object> map);

    List<TransactionReportVO> queryHistoryTransactionReports(String group, String domain, String startTime, String endTime,
                                                             TransactionReportType type);

    List<TransactionReportVO> queryHistoryTransactionReports(String group, String domain, String startTime, String endTime,
                                                             TransactionReportType type, Map<String, Object> map);

    List<TransactionReportVO> queryTransactionReportsByType(String group, String domain, String startTime, String typeName,
                                                            TransactionReportType type);

    List<TransactionReportVO> queryHistoryClientTransactionReports(String group, String domain, String startTime, String endTime, TransactionReportType type);

    List<TransactionReportVO> queryHistoryClientTransactionReports(String group,
                                                                          String domain,
                                                                          String startTime,
                                                                          String endTime,
                                                                          TransactionReportType type,
                                                                          Map<String, Object> map);

    void storeRealtimeTransactionReport(TransactionReportVO transactionReportVO) throws StorageException;

    void storeHistoryTransactionReport(TransactionReportVO transactionReportVO);


}
