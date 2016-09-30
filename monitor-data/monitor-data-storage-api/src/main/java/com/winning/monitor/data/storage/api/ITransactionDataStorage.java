package com.winning.monitor.data.storage.api;

import com.winning.monitor.data.api.transaction.vo.TransactionReportType;
import com.winning.monitor.data.api.transaction.vo.TransactionReportVO;
import com.winning.monitor.data.storage.api.exception.StorageException;

import java.util.List;

/**
 * Created by nicholasyan on 16/9/14.
 */
public interface ITransactionDataStorage {

    List<TransactionReportVO> queryTransactionReports(String domain, String startTime, TransactionReportType type);

    List<TransactionReportVO> queryTransactionReports(String domain, String startTime,String endTime,
                                                      TransactionReportType type);

    List<TransactionReportVO> queryTransactionReportsByType(String domain, String startTime, String typeName,
                                                            TransactionReportType type);

    void storeTransactionReport(TransactionReportVO transactionReportVO) throws StorageException;

    void storeHistoryTransactionReport(TransactionReportVO transactionReportVO);
}
