package com.winning.monitor.data.transaction;

import com.winning.monitor.data.api.ITransactionDataQuery;
import com.winning.monitor.data.api.transaction.TransactionReportMerger;
import com.winning.monitor.data.api.transaction.vo.TransactionReportType;
import com.winning.monitor.data.api.transaction.vo.TransactionReportVO;
import com.winning.monitor.data.api.transaction.vo.TransactionTypeVO;
import com.winning.monitor.data.storage.api.ITransactionDataStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nicholasyan on 16/9/14.
 */
@Service
public class TransactionDataQuery implements ITransactionDataQuery {

    @Autowired
    private ITransactionDataStorage transactionDataStorage;


    @Override
    public TransactionReportVO queryTransactionReport(String domain, String startTime,
                                                      TransactionReportType type) {

        List<TransactionReportVO> reports =
                transactionDataStorage.queryRealtimeTransactionReports(domain, startTime);


        TransactionReportMerger transactionReportMerger = new TransactionReportMerger();
        TransactionReportVO transactionReport = transactionReportMerger.mergeReports(reports);

        return transactionReport;
    }

    @Override
    public TransactionTypeVO queryByTranscationType(String domain, String startTime, String typeName, TransactionReportType type) {
        List<TransactionReportVO> reports =
                transactionDataStorage.queryTransactionReportsByType(domain, startTime, typeName, type);
        return null;
    }
}
