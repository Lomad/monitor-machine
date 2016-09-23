package com.winning.monitor.data.storage.mongodb.po.transaction;

import com.winning.monitor.data.api.transaction.vo.TransactionMachineVO;
import com.winning.monitor.data.api.transaction.vo.TransactionTypeVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicholasyan on 16/9/18.
 */
public class TransactionMachinePO {

    private String ip;
    private List<TransactionTypePO> transactionTypes = new ArrayList<>();

    public TransactionMachinePO() {

    }

    public TransactionMachinePO(TransactionMachineVO transactionMachineVO) {
        this.ip = transactionMachineVO.getIp();
        if (transactionMachineVO.getTransactionTypes() != null) {
            for (TransactionTypeVO transactionTypeVO : transactionMachineVO.getTransactionTypes()) {
                this.transactionTypes.add(new TransactionTypePO(transactionTypeVO));
            }
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<TransactionTypePO> getTransactionTypes() {
        return transactionTypes;
    }

    public void setTransactionTypes(List<TransactionTypePO> transactionTypes) {
        this.transactionTypes = transactionTypes;
    }
}
