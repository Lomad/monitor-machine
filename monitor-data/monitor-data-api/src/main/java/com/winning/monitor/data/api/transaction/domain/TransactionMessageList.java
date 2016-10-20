package com.winning.monitor.data.api.transaction.domain;

import java.util.List;

/**
 * Created by nicholasyan on 16/10/20.
 */
public class TransactionMessageList {

    private List<TransactionMessage> transactionMessages;

    private long totalSize;

    public List<TransactionMessage> getTransactionMessages() {
        return transactionMessages;
    }

    public void setTransactionMessages(List<TransactionMessage> transactionMessages) {
        this.transactionMessages = transactionMessages;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }
}
