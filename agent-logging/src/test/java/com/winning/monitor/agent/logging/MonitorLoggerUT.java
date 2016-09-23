package com.winning.monitor.agent.logging;

import com.winning.monitor.agent.logging.transaction.Transaction;
import org.junit.Test;

/**
 * Created by nicholasyan on 16/9/8.
 */
public class MonitorLoggerUT {


    @Test
    public void testTransaction() throws InterruptedException {
        Transaction transaction = MonitorLogger.newTransaction("SQL ", "SELECT ");
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


    @Test
    public void testTransactionRunning() throws InterruptedException {
        transactionThread();
        Thread.sleep(60000);
    }

    @Test
    public void testTransaction2K() throws InterruptedException {
        for (int i = 0; i < 2000; i++) {
            Transaction transaction = MonitorLogger.newTransaction("SQL2", "SELECT 2");
            transaction.setStatus(Transaction.SUCCESS);
            transaction.complete();
        }
        Thread.sleep(300000);
        System.out.println("complete");
    }

    private void transactionThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 2000; i++) {
                    Transaction transaction = MonitorLogger.newTransaction("SQL1", "SELECT 1");
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    transaction.setStatus(Transaction.SUCCESS);
                    transaction.complete();
                }
                System.out.println("complete");
            }
        });
        thread.start();
    }


    @Test
    public void testNestedTransaction() throws InterruptedException {
        Transaction parentTransaction = MonitorLogger.newTransaction("PARENT", "HELLO");
        Transaction childTransaction = MonitorLogger.newTransaction("CHILD", "HELLO");
        Thread.sleep(10);
        childTransaction.success();
        parentTransaction.success();
        Thread.sleep(10000);
    }

}
