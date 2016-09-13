package com.winning.monitor.superisor.consumer.logging.transaction.dto;

import com.winning.monitor.superisor.consumer.logging.transaction.entity.AllDuration;
import com.winning.monitor.superisor.consumer.logging.transaction.entity.Range2;

import java.util.List;
import java.util.Map;

/**
 * Created by nicholasyan on 16/9/12.
 */
public class TransactionTypeDTO {

    private String id;

    private String name;

    private long totalCount;

    private long failCount;

    private double failPercent;

    private double min = 86400000d;

    private double max = -1d;

    private double sum;

    private double sum2;

    private List<TransactionNameDTO> transactionNames;

    private Map<Integer, Range2> range2s;

    private Map<Integer, AllDuration> allDurations;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getFailCount() {
        return failCount;
    }

    public void setFailCount(long failCount) {
        this.failCount = failCount;
    }

    public double getFailPercent() {
        return failPercent;
    }

    public void setFailPercent(double failPercent) {
        this.failPercent = failPercent;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getSum2() {
        return sum2;
    }

    public void setSum2(double sum2) {
        this.sum2 = sum2;
    }

    public List<TransactionNameDTO> getTransactionNames() {
        return transactionNames;
    }

    public void setTransactionNames(List<TransactionNameDTO> transactionNames) {
        this.transactionNames = transactionNames;
    }

    public Map<Integer, Range2> getRange2s() {
        return range2s;
    }

    public void setRange2s(Map<Integer, Range2> range2s) {
        this.range2s = range2s;
    }

    public Map<Integer, AllDuration> getAllDurations() {
        return allDurations;
    }

    public void setAllDurations(Map<Integer, AllDuration> allDurations) {
        this.allDurations = allDurations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
