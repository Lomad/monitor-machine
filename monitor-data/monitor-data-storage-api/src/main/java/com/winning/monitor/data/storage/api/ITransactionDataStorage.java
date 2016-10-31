package com.winning.monitor.data.storage.api;

import com.winning.monitor.data.api.transaction.vo.TransactionReportType;
import com.winning.monitor.data.api.transaction.vo.TransactionReportVO;
import com.winning.monitor.data.storage.api.exception.StorageException;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by nicholasyan on 16/9/14.
 */
public interface ITransactionDataStorage {


    LinkedHashSet<String> findAllTransactionDomains();

    LinkedHashSet<String> findAllServerIpAddress(String domain);

    List<TransactionReportVO> queryRealtimeTransactionReports(String domain, String startTime);

    List<TransactionReportVO> queryRealtimeTransactionReports(String domain, String startTime, String endTime);

    List<TransactionReportVO> queryRealtimeTransactionReports(Map<String, Object> map);

    List<TransactionReportVO> queryRealtimeTransactionReports(String domain, String startTime, String endTime, Map<String, Object> map);

    List<TransactionReportVO> queryHistoryTransactionReports(String domain, String startTime, String endTime,
                                                             TransactionReportType type);

    List<TransactionReportVO> queryHistoryTransactionReports(String domain, String startTime, String endTime,
                                                             TransactionReportType type, Map<String, Object> map);

    List<TransactionReportVO> queryTransactionReportsByType(String domain, String startTime, String typeName,
                                                            TransactionReportType type);

    void storeRealtimeTransactionReport(TransactionReportVO transactionReportVO) throws StorageException;

    void storeHistoryTransactionReport(TransactionReportVO transactionReportVO);


}
