package com.winning.monitor.data.api.transaction.vo;

/**
 * Created by sao something on 2016/11/29.
 */
public class SumVO {
    private long totalSum;
    private long failSum;

    public long getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(long totalSum) {
        this.totalSum = totalSum;
    }

    public long getFailSum() {
        return failSum;
    }

    public void setFailSum(long failSum) {
        this.failSum = failSum;
    }
}
