package com.winning.monitor.data.transaction;

import com.winning.monitor.data.api.ITransactionDataQuery;
import com.winning.monitor.data.api.transaction.TransactionReportMerger;
import com.winning.monitor.data.api.transaction.vo.*;
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
                transactionDataStorage.queryTransactionReports(domain, startTime, type);


        if (reports == null || reports.size() == 0)
            return null;

        TransactionReportVO transactionReport = reports.get(0);
        if (reports.size() > 1) {
            for (int i = 1; i < reports.size(); i++)
                TransactionReportMerger.mergeReport(transactionReport, reports.get(i));
        }

        for (TransactionMachineVO machineVO : transactionReport.getMachines()) {
            for (TransactionTypeVO typeVO : machineVO.getTransactionTypes()) {
                for (TransactionNameVO nameVO : typeVO.getTransactionNames()) {
                    nameVO.setAvg(Math.ceil(nameVO.getAvg()));
                    nameVO.setFailPercent(Math.ceil(nameVO.getFailPercent()));
                    nameVO.setLine95Value(Math.ceil(nameVO.getLine95Value()));
                    nameVO.setLine99Value(Math.ceil(nameVO.getLine99Value()));
                    nameVO.setStd(Math.ceil(nameVO.getStd()));
                    nameVO.setTps(Math.round(nameVO.getTps() * 100) / 100.0);
                }
                typeVO.setAvg(Math.ceil(typeVO.getAvg()));
                typeVO.setFailPercent(Math.ceil(typeVO.getFailPercent()));
                typeVO.setLine95Value(Math.ceil(typeVO.getLine95Value()));
                typeVO.setLine99Value(Math.ceil(typeVO.getLine99Value()));
                typeVO.setStd(Math.ceil(typeVO.getStd()));
                typeVO.setTps(Math.round(typeVO.getTps() * 100) / 100.0);
            }
        }

        return transactionReport;
    }

    @Override
    public TransactionTypeVO queryByTranscationType(String domain, String startTime, String typeName, TransactionReportType type) {
        List<TransactionReportVO> reports =
                transactionDataStorage.queryTransactionReportsByType(domain, startTime, typeName, type);
        return null;
    }
}
