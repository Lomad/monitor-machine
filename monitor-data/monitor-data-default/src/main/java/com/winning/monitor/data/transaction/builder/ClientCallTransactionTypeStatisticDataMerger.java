package com.winning.monitor.data.transaction.builder;

import com.winning.monitor.data.api.transaction.TransactionReportMerger;
import com.winning.monitor.data.api.transaction.domain.TransactionStatisticData;
import com.winning.monitor.data.api.transaction.domain.TransactionStatisticReport;
import com.winning.monitor.data.api.transaction.vo.TransactionClientVO;
import com.winning.monitor.data.api.transaction.vo.TransactionMachineVO;
import com.winning.monitor.data.api.transaction.vo.TransactionReportVO;
import com.winning.monitor.data.api.transaction.vo.TransactionTypeVO;
import com.winning.monitor.data.api.vo.Range2;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by nicholasyan on 16/10/21.
 */
public class ClientCallTransactionTypeStatisticDataMerger {

    private final static TransactionReportMerger transactionMerger = new TransactionReportMerger();
    private Map<String, Map<String, TransactionTypeVO>> transactionTypeMap;
    private String clientDomain;
    private LinkedHashMap<Integer, Range2> range2;

    public ClientCallTransactionTypeStatisticDataMerger(String clientDomain) {
        this.clientDomain = clientDomain;
        this.transactionTypeMap = new LinkedHashMap<>();
        this.range2 = new LinkedHashMap<>();
    }

    public void add(TransactionReportVO report) {
        for (TransactionMachineVO machine : report.getMachines()) {
            for (TransactionClientVO client : machine.getTransactionClients()) {
                if (StringUtils.isEmpty(client.getDomain()) || !clientDomain.equals(client.getDomain()))
                    continue;

                Map<String, TransactionTypeVO> clientDomainMap = this.transactionTypeMap.get(client.getDomain());
                    if (clientDomainMap == null) {
                        clientDomainMap = new LinkedHashMap<>();
                        this.transactionTypeMap.put(client.getDomain(), clientDomainMap);
                }

                for (TransactionTypeVO transactionType : client.getTransactionTypes()) {
                    //如果是统计TransactionType数据
                    TransactionTypeVO oldTransactionType =
                            clientDomainMap.get(transactionType.getName());

                    //当前AppClientDomain不存在,则加入当前的AppClientDomain
                    if (oldTransactionType == null) {
                        oldTransactionType = new TransactionTypeVO();
                        oldTransactionType.setId(transactionType.getId());
                        oldTransactionType.setName(transactionType.getName());
                        clientDomainMap.put(transactionType.getName(), oldTransactionType);
                    }

                    //合并TransactionType
                    transactionMerger.mergeType(oldTransactionType, transactionType);
                }
            }
        }
    }


    public TransactionStatisticReport toTransactionStatisticReport() {
        TransactionStatisticReport transactionStatisticReport = new TransactionStatisticReport();

        for (Map.Entry<String, Map<String, TransactionTypeVO>> entry : this.transactionTypeMap.entrySet()) {
            String setClientAppName = entry.getKey();
            Map<String, TransactionTypeVO> transactionTypeMap = entry.getValue();

            for (Map.Entry<String, TransactionTypeVO> callerEntry : transactionTypeMap.entrySet()) {
                TransactionTypeVO transactionTypeVO = callerEntry.getValue();
                TransactionStatisticData statisticData =
                        this.toTransactionStatisticData(setClientAppName, transactionTypeVO);
                transactionStatisticReport.addTransactionStatisticData(statisticData);
            }
        }
        transactionStatisticReport.setTotalSize(transactionStatisticReport.getTransactionStatisticDatas().size());
        return transactionStatisticReport;
    }


    private TransactionStatisticData toTransactionStatisticData(String clintDomain,
                                                                TransactionTypeVO transactionType) {

        TransactionStatisticData statisticData = new TransactionStatisticData();
        statisticData.setClientAppName(clintDomain);
        statisticData.setTransactionTypeName(transactionType.getName());

        statisticData.setTotalCount(transactionType.getTotalCount());
        statisticData.setFailCount(transactionType.getFailCount());
        statisticData.setFailPercent(Math.ceil(transactionType.getFailPercent()));
        statisticData.setMin(transactionType.getMin());
        statisticData.setMax(transactionType.getMax());
        statisticData.setSum(transactionType.getSum());

        statisticData.setAvg(Math.round(transactionType.getAvg() * 100) / 100.0);
        statisticData.setStd(Math.ceil(transactionType.getStd()));
        statisticData.setTps(Math.round(transactionType.getTps() * 100) / 100.0);
        statisticData.setLine95Value(Math.round(transactionType.getLine95Value() * 100) / 100.0);
        statisticData.setLine99Value(Math.round(transactionType.getLine99Value() * 100) / 100.0);

        return statisticData;
    }

    public LinkedHashMap<Integer, Range2> getRange2s() {
        return this.range2;
    }

}
