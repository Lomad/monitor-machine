package com.winning.monitor.data.api;

import com.winning.monitor.data.api.transaction.vo.TransactionReportType;
import com.winning.monitor.data.api.transaction.vo.TransactionReportVO;
import com.winning.monitor.data.api.transaction.vo.TransactionTypeVO;

/**
 * Created by nicholasyan on 16/9/14.
 */
@Deprecated
public interface ITransactionDataQuery {

    TransactionReportVO queryTransactionReport(String group,String domain, String startTime, TransactionReportType type);

    TransactionTypeVO queryByTranscationType(String group, String domain, String startTime, String typeName,
                                             TransactionReportType type);


}