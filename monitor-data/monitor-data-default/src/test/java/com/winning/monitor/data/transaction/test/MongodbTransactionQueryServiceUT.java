package com.winning.monitor.data.transaction.test;

import com.winning.monitor.agent.logging.message.Caller;
import com.winning.monitor.data.api.ITransactionDataQueryService;
import com.winning.monitor.data.api.transaction.domain.TransactionCallTimesReport;
import com.winning.monitor.data.api.transaction.domain.TransactionMessageList;
import com.winning.monitor.data.api.transaction.domain.TransactionStatisticReport;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Created by nicholasyan on 16/9/14.
 */
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*-context.xml"})
public class MongodbTransactionQueryServiceUT extends
        AbstractJUnit4SpringContextTests {

    @Autowired
    private ITransactionDataQueryService transactionDataQuery;

    @Test
    public void testQueryDomains() {
        LinkedHashSet<String> set = transactionDataQuery.getAllServerAppNames();
        System.out.println(set);
    }


    @Test
    public void testCallerHashcode() {
        HashMap<Object, String> map = new HashMap<>();
        Caller caller = new Caller();
        caller.setName("name");
        caller.setType("type");
        caller.setIp("ip");
        Caller caller1 = new Caller();
        caller1.setName("name");
        caller1.setType("type");
        caller1.setIp("ip");

        map.put(caller, "1");
        map.put(caller1, "2");

        Assert.assertTrue(map.get(caller).equals("2"));
    }


    @Test
    public void testQueryIps() {
        LinkedHashSet<String> set = transactionDataQuery.getAllServerIpAddress("test1");
        System.out.println(set);
    }


    @Test
    public void testQueryLastHourTransactionTypeReportByServer() {
        TransactionStatisticReport report = transactionDataQuery.queryLastHourTransactionTypeReportByServer("test1");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryHourTransactionTypeReportByServer() {
        TransactionStatisticReport report = transactionDataQuery.queryHourTransactionTypeReportByServer("test1","2016-10-27 17:19:00") ;
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryDayTransactionTypeReportByServer() {
        TransactionStatisticReport report = transactionDataQuery.queryDayTransactionTypeReportByServer("test1","2016-10-24");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryWeekTransactionTypeReportByServer() {
        TransactionStatisticReport report = transactionDataQuery.queryWeekTransactionTypeReportByServer("test1","2016-10-24");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryMonthTransactionTypeReportByServer(){
        TransactionStatisticReport report = transactionDataQuery.queryMonthTransactionTypeReportByServer("test1","2016-10-01");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryTodayTransactionTypeReportByServer() {
        TransactionStatisticReport report = transactionDataQuery.queryTodayTransactionTypeReportByServer("test1");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryLastHourTransactionTypeReportByClient() {
        TransactionStatisticReport report =
                transactionDataQuery.queryLastHourTransactionTypeReportByClient("test1", "挂号", "");
        Assert.assertNotNull(report);
        report =
                transactionDataQuery.queryLastHourTransactionTypeReportByClient("test1", "收费", "10.0.0.15");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryHourTransactionTypeReportByClient(){
        TransactionStatisticReport report =
                transactionDataQuery.queryHourTransactionTypeReportByClient("test1","2016-10-31 15:00:00", "挂号", "");
        Assert.assertNotNull(report);
        report =
                transactionDataQuery.queryHourTransactionTypeReportByClient("test1","2016-10-31 15:00:00", "收费", "10.0.0.15");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryTodayTransactionTypeReportByClient() {
        TransactionStatisticReport report =
                transactionDataQuery.queryTodayTransactionTypeReportByClient("test1", "挂号", "");
        Assert.assertNotNull(report);
        report =
                transactionDataQuery.queryTodayTransactionTypeReportByClient("test1", "收费", "10.0.0.15");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryLastHourTransactionTypeCallTimesReportByServer() {
        TransactionCallTimesReport report =
                transactionDataQuery.queryLastHourTransactionTypeCallTimesReportByServer("test1", "挂号", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryHourTransactionTypeCallTimesReportByServer(){
        TransactionCallTimesReport report =
                transactionDataQuery.queryHourTransactionTypeCallTimesReportByServer("test1", "2016-10-31 15:00:00", "挂号", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryTodayTransactionTypeCallTimesReportByServer() {
        TransactionCallTimesReport report =
                transactionDataQuery.queryTodayTransactionTypeCallTimesReportByServer("test1", "挂号", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryLastHourTransactionNameReportByServer() {
        TransactionStatisticReport report =
                transactionDataQuery.queryLastHourTransactionNameReportByServer("test1", "挂号", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryHourTransactionNameReportByServer() {
        TransactionStatisticReport report =
                transactionDataQuery.queryHourTransactionNameReportByServer("test1","2016-10-31 15:00:00", "挂号", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryTodayTransactionNameReportByServer() {
        TransactionStatisticReport report =
                transactionDataQuery.queryTodayTransactionNameReportByServer("test1", "挂号", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryLastHourTransactionMessageList() {

        LinkedHashMap<String, String> order = new LinkedHashMap<>();
        order.put("time", "ASC");

        TransactionMessageList transactionMessageList =
                transactionDataQuery.queryLastHourTransactionMessageList("test1", "挂号", "", "", "", "", "成功", 0, 100, order);

        Assert.assertNotNull(transactionMessageList);
    }

    @Test
    public void testQueryHourTransactionMessageList() {

        LinkedHashMap<String, String> order = new LinkedHashMap<>();
        order.put("time", "ASC");

        TransactionMessageList transactionMessageList =
                transactionDataQuery.queryHourTransactionMessageList("test1", "2016-10-31 15:00:00","挂号", "", "", "", "", "成功", 0, 100, order);

        Assert.assertNotNull(transactionMessageList);
    }

    @Test
    public void testQueryTodayTransactionMessageList() {

        LinkedHashMap<String, String> order = new LinkedHashMap<>();
        order.put("time", "ASC");

        TransactionMessageList transactionMessageList =
                transactionDataQuery.queryTodayTransactionMessageList("test1", "挂号", "", "", "", "", "成功", 0, 100, order);

        Assert.assertNotNull(transactionMessageList);
    }

    @Test
    public void testComputerHour() {
        int hour = Integer.parseInt("2016-01-01 20:00:00".substring(11, 13));
        Assert.assertTrue(hour == 20);
    }

}
