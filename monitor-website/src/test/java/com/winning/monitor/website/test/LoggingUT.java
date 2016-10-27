package com.winning.monitor.website.test;

import com.winning.monitor.agent.logging.MonitorLogger;
import com.winning.monitor.agent.logging.transaction.Transaction;
import org.junit.Test;

/**
 * Created by nicholasyan on 16/10/24.
 */
public class LoggingUT {

    @Test
    public void testTransactionData() throws InterruptedException {
        MonitorLogger.checkAndInitialize();

        while (true) {
            MonitorLogger.setCaller("His", "192.16.0.1", "PC");//调用方
            Transaction parentTransaction = MonitorLogger.beginTransactionType("收费");//大服务
            parentTransaction.addData("data1", "data1");

            Transaction childTransaction = MonitorLogger.beginTransactionName(parentTransaction, "读取数据库");//步骤
            Thread.sleep(10);
            childTransaction.success();

            childTransaction = MonitorLogger.beginTransactionName(parentTransaction, "写入数据库");//步骤
            Thread.sleep(10);
            childTransaction.success();

            parentTransaction.success();
            Thread.sleep(1000);
        }
    }

}
