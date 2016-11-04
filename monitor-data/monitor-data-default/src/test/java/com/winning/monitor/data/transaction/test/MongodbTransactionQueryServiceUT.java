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
        LinkedHashSet<String> set = transactionDataQuery.getAllServerAppNames("BI");
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
        LinkedHashSet<String> set = transactionDataQuery.getAllServerIpAddress("BI","test1");
        System.out.println(set);
    }


    @Test
    public void testQueryLastHourTransactionTypeReportByServer() {
        TransactionStatisticReport report = transactionDataQuery.queryLastHourTransactionTypeReportByServer("BI","test1");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryHourTransactionTypeReportByServer() {
        TransactionStatisticReport report = transactionDataQuery.queryHourTransactionTypeReportByServer("BI","test1","2016-11-02 10:00:00") ;
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryDayTransactionTypeReportByServer() {
        TransactionStatisticReport report = transactionDataQuery.queryDayTransactionTypeReportByServer("BI","test1","2016-10-24");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryWeekTransactionTypeReportByServer() {
        TransactionStatisticReport report = transactionDataQuery.queryWeekTransactionTypeReportByServer("BI", "test1","2016-10-24");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryMonthTransactionTypeReportByServer(){
        TransactionStatisticReport report = transactionDataQuery.queryMonthTransactionTypeReportByServer("BI", "test1","2016-10-01");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryTodayTransactionTypeReportByServer() {
        TransactionStatisticReport report = transactionDataQuery.queryTodayTransactionTypeReportByServer("BI","test1");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryLastHourTransactionTypeReportByClient() {
        TransactionStatisticReport report =
                transactionDataQuery.queryLastHourTransactionTypeReportByClient("BI", "test1", "挂号", "");
        Assert.assertNotNull(report);
        report =
                transactionDataQuery.queryLastHourTransactionTypeReportByClient("BI", "test1", "收费", "10.0.0.15");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryHourTransactionTypeReportByClient(){
        TransactionStatisticReport report =
                transactionDataQuery.queryHourTransactionTypeReportByClient("BI", "test1","2016-10-31 15:00:00", "挂号", "");
        Assert.assertNotNull(report);
        report =
                transactionDataQuery.queryHourTransactionTypeReportByClient("BI", "test1","2016-10-31 15:00:00", "收费", "10.0.0.15");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryTodayTransactionTypeReportByClient() {
        TransactionStatisticReport report =
                transactionDataQuery.queryTodayTransactionTypeReportByClient("BI", "test1", "挂号", "");
        Assert.assertNotNull(report);
        report =
                transactionDataQuery.queryTodayTransactionTypeReportByClient("BI","test1", "收费", "10.0.0.15");
        Assert.assertNotNull(report);
    }



    @Test
    public void testQueryDayTransactionTypeReportByClient() {
        TransactionStatisticReport report =
                transactionDataQuery.queryDayTransactionTypeReportByClient("BI", "test1","2016-10-31", "挂号", "");
        Assert.assertNotNull(report);
        report =
                transactionDataQuery.queryDayTransactionTypeReportByClient("BI", "test1","2016-10-31", "收费", "10.0.0.15");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryWeekTransactionTypeReportByClient() {
        TransactionStatisticReport report =
                transactionDataQuery.queryWeekTransactionTypeReportByClient("BI", "test1","2016-10-24", "挂号", "");
        Assert.assertNotNull(report);
        report =
                transactionDataQuery.queryWeekTransactionTypeReportByClient("BI", "test1","2016-10-24", "收费", "10.0.0.15");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryMonthTransactionTypeReportByClient() {
        TransactionStatisticReport report =
                transactionDataQuery.queryMonthTransactionTypeReportByClient("BI", "test1","2016-10-01", "挂号", "");
        Assert.assertNotNull(report);
        report =
                transactionDataQuery.queryMonthTransactionTypeReportByClient("BI", "test1","2016-10-01", "收费", "10.0.0.15");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryLastHourTransactionTypeCallTimesReportByServer() {
        TransactionCallTimesReport report =
                transactionDataQuery.queryLastHourTransactionTypeCallTimesReportByServer("BI", "test-group", "门诊收费服务", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryHourTransactionTypeCallTimesReportByServer(){
        TransactionCallTimesReport report =
                transactionDataQuery.queryHourTransactionTypeCallTimesReportByServer("BI", "test1", "2016-10-31 15:00:00", "挂号", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryTodayTransactionTypeCallTimesReportByServer() {
        TransactionCallTimesReport report =
                transactionDataQuery.queryTodayTransactionTypeCallTimesReportByServer("BI", "test1", "挂号", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryDayTransactionTypeCallTimesReportByServer() {
        TransactionCallTimesReport report =
                transactionDataQuery.queryDayTransactionTypeCallTimesReportByServer("BI", "test1","2016-11-02", "门诊收费服务", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryWeekTransactionTypeCallTimesReportByServer() {
        TransactionCallTimesReport report =
                transactionDataQuery.queryWeekTransactionTypeCallTimesReportByServer("BI", "test-group","2016-10-31", "门诊收费服务", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryMonthTransactionTypeCallTimesReportByServer() {
        TransactionCallTimesReport report =
                transactionDataQuery.queryMonthTransactionTypeCallTimesReportByServer("BI", "test1","2016-10-01", "挂号", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryLastHourTransactionNameReportByServer() {
        TransactionStatisticReport report =
                transactionDataQuery.queryLastHourTransactionNameReportByServer("BI", "test1", "挂号", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryHourTransactionNameReportByServer() {
        TransactionStatisticReport report =
                transactionDataQuery.queryHourTransactionNameReportByServer("BI", "test1","2016-10-31 15:00:00", "挂号", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryTodayTransactionNameReportByServer() {
        TransactionStatisticReport report =
                transactionDataQuery.queryTodayTransactionNameReportByServer("BI", "test1", "挂号", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryDayTransactionNameReportByServer() {
        TransactionStatisticReport report =
                transactionDataQuery.queryDayTransactionNameReportByServer("BI", "test1","2016-10-21", "挂号", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryWeekTransactionNameReportByServer() {
        TransactionStatisticReport report =
                transactionDataQuery.queryWeekTransactionNameReportByServer("BI", "test1","2016-10-24", "挂号", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryMonthTransactionNameReportByServer() {
        TransactionStatisticReport report =
                transactionDataQuery.queryMonthTransactionNameReportByServer("BI", "test1","2016-10-01", "挂号", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryLastHourTransactionMessageList() {

        LinkedHashMap<String, String> order = new LinkedHashMap<>();
        order.put("time", "ASC");

        TransactionMessageList transactionMessageList =
                transactionDataQuery.queryLastHourTransactionMessageList("BI", "test1", "挂号", "", "", "", "", "成功", 0, 100, order);

        Assert.assertNotNull(transactionMessageList);
    }

    @Test
    public void testQueryHourTransactionMessageList() {

        LinkedHashMap<String, String> order = new LinkedHashMap<>();
        order.put("time", "ASC");

        TransactionMessageList transactionMessageList =
                transactionDataQuery.queryHourTransactionMessageList("BI", "test1", "2016-10-31 15:00:00","挂号", "", "", "", "", "成功", 0, 100, order);

        Assert.assertNotNull(transactionMessageList);
    }

    @Test
    public void testQueryTodayTransactionMessageList() {

        LinkedHashMap<String, String> order = new LinkedHashMap<>();
        order.put("time", "ASC");

        TransactionMessageList transactionMessageList =
                transactionDataQuery.queryTodayTransactionMessageList("BI", "test1", "挂号", "", "", "", "", "成功", 0, 100, order);

        Assert.assertNotNull(transactionMessageList);
    }

    @Test
    public void testQueryDayTransactionMessageList() {

        LinkedHashMap<String, String> order = new LinkedHashMap<>();
        order.put("time", "ASC");

        TransactionMessageList transactionMessageList =
                transactionDataQuery.queryDayTransactionMessageList("BI","test1", "2016-11-01", "挂号", "", "", "", "", "失败", 0, 100, order);

        Assert.assertNotNull(transactionMessageList);
    }

    @Test
    public void testQueryWeekTransactionMessageList() {

        LinkedHashMap<String, String> order = new LinkedHashMap<>();
        order.put("time", "ASC");

        TransactionMessageList transactionMessageList =
                transactionDataQuery.queryWeekTransactionMessageList("BI", "test1", "2016-10-24", "挂号", "", "", "", "", "成功", 0, 100, order);

        Assert.assertNotNull(transactionMessageList);
    }

    @Test
    public void testQueryMonthTransactionMessageList() {

        LinkedHashMap<String, String> order = new LinkedHashMap<>();
        order.put("time", "ASC");

        TransactionMessageList transactionMessageList =
                transactionDataQuery.queryMonthTransactionMessageList("BI", "test1", "2016-10-01", "挂号", "", "", "", "", "成功", 0, 100, order);

        Assert.assertNotNull(transactionMessageList);
    }

    @Test
    public void testComputerHour() {
        int hour = Integer.parseInt("2016-01-01 20:00:00".substring(11, 13));
        Assert.assertTrue(hour == 20);
    }

}
