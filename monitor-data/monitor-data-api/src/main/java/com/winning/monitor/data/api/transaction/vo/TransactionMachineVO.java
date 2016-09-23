package com.winning.monitor.data.api.transaction.vo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nicholasyan on 16/9/12.
 */
public class TransactionMachineVO {

    private String ip;

    private List<TransactionTypeVO> transactionTypes = new ArrayList<>();
    private Map<String, TransactionTypeVO> typeMap = new LinkedHashMap<String, TransactionTypeVO>();


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<TransactionTypeVO> getTransactionTypes() {
        return transactionTypes;
    }

    public void setTransactionTypes(List<TransactionTypeVO> transactionTypes) {
        this.transactionTypes = transactionTypes;
        this.typeMap.clear();
        if (transactionTypes == null)
            return;
        for (TransactionTypeVO transactionType : transactionTypes) {
            this.typeMap.put(transactionType.getId(), transactionType);
        }
    }

    public void addTransactionType(TransactionTypeVO transactionType) {
        this.transactionTypes.add(transactionType);
        this.typeMap.put(transactionType.getId(), transactionType);
    }

    public TransactionTypeVO getTransactionType(String id) {
        return this.typeMap.get(id);
    }
}
