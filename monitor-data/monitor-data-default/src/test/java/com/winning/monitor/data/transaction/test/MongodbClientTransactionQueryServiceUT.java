package com.winning.monitor.data.transaction.test;

import com.winning.monitor.data.api.IClientTransactionDataQueryService;
import com.winning.monitor.data.api.ITransactionDataQueryService;
import com.winning.monitor.data.api.transaction.domain.TransactionCallTimesReport;
import com.winning.monitor.data.api.transaction.domain.TransactionStatisticReport;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.LinkedHashSet;

/**
 * Created by wangwh on 2016/11/3.
 */
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*-context.xml"})
public class MongodbClientTransactionQueryServiceUT extends
        AbstractJUnit4SpringContextTests {


    @Autowired
    private IClientTransactionDataQueryService IClienttransactionDataQuery;

    @Test
    public void testQueryClientDomains() {
        LinkedHashSet<String> set = IClienttransactionDataQuery.getAllClientNames("BI");
        System.out.println(set);
    }


    @Test
    public void testQueryLastHourClientReportByClient() {
        TransactionStatisticReport report = IClienttransactionDataQuery.queryLastHourClientReportByClient("BI","HIS");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryTodayClientReportByClient() {
        TransactionStatisticReport report = IClienttransactionDataQuery.queryTodayClientTypeReportByClient("BI","HIS");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryHourClientReportByClient() {
        TransactionStatisticReport report = IClienttransactionDataQuery.queryHourClientReportByClient("BI","HIS","2016-11-03 15:00:00");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryDayClientReportByClient() {
        TransactionStatisticReport report = IClienttransactionDataQuery.queryDayClientReportByClient("BI", "His", "2016-10-27");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryWeekClientReportByClient() {
        TransactionStatisticReport report = IClienttransactionDataQuery.queryWeekClientReportByClient("BI", "HIS", "2016-10-31");
        Assert.assertNotNull(report);
    }


    @Test
    public void testQueryMonthClientReportByClient() {
        TransactionStatisticReport report = IClienttransactionDataQuery.queryMonthClientReportByClient("BI", "HIS", "2016-10-01");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryLastHourTransactionTypeCallTimesReportByServer(){
        TransactionCallTimesReport report =
                IClienttransactionDataQuery.queryLastHourTransactionTypeCallTimesReportByClient("BI", "HIS","test1","挂号");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryTodayTransactionTypeCallTimesReportByServer(){
        TransactionCallTimesReport report =
                IClienttransactionDataQuery.queryTodayTransactionTypeCallTimesReportByClient("BI", "HIS","test1","挂号");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryHourTransactionTypeCallTimesReportByServer(){
        TransactionCallTimesReport report =
                IClienttransactionDataQuery.queryHourTransactionTypeCallTimesReportByClient("BI", "HIS","test1","2016-11-3 15:00:00","挂号");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryDayTransactionTypeCallTimesReportByServer(){
        TransactionCallTimesReport report =
                IClienttransactionDataQuery.queryDayTransactionTypeCallTimesReportByClient("BI", "HIS","test1","2016-11-3","挂号");
        Assert.assertNotNull(report);
    }


    @Test
    public void testQueryWeekTransactionTypeCallTimesReportByServer(){
        TransactionCallTimesReport report =
                IClienttransactionDataQuery.queryWeekTransactionTypeCallTimesReportByClient("BI", "HIS","test1","2016-10-31","挂号");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryMonthTransactionTypeCallTimesReportByServer(){
        TransactionCallTimesReport report =
                IClienttransactionDataQuery.queryMonthTransactionTypeCallTimesReportByClient("BI", "HIS","test1","2016-10-31","挂号");
        Assert.assertNotNull(report);
    }

}
