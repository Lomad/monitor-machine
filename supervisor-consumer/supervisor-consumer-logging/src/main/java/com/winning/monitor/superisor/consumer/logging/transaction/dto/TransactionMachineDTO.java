package com.winning.monitor.superisor.consumer.logging.transaction.dto;

import java.util.List;

/**
 * Created by nicholasyan on 16/9/12.
 */
public class TransactionMachineDTO {

    private String ip;

    private List<TransactionTypeDTO> transactionTypes;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<TransactionTypeDTO> getTransactionTypes() {
        return transactionTypes;
    }

    public void setTransactionTypes(List<TransactionTypeDTO> transactionTypes) {
        this.transactionTypes = transactionTypes;
    }
}
