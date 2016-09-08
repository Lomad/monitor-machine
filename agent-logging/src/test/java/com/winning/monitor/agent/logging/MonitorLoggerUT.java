package com.winning.monitor.agent.logging;

import com.winning.monitor.agent.logging.transaction.Transaction;
import org.junit.Test;

/**
 * Created by nicholasyan on 16/9/8.
 */
public class MonitorLoggerUT {


    @Test
    public void testTransaction() throws InterruptedException {
        Transaction transaction = MonitorLogger.newTransaction("SQL ", "SELECT   ");
        Thread.sleep(1000);
        transaction.setStatus(Transaction.SUCCESS);
        transaction.complete();

        System.out.println(transaction.getDurationInMillis());
        System.out.println(transaction.getDurationInMicros());

        transaction = MonitorLogger.newTransaction("SQL", "SELECT");
        Thread.sleep(2000);
        transaction.setStatus(Transaction.SUCCESS);
        transaction.complete();

        System.out.println(transaction.getDurationInMillis());
        System.out.println(transaction.getDurationInMicros());

    }


}
