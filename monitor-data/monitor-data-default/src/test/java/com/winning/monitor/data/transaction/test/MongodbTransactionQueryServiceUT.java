package com.winning.monitor.data.transaction.test;

import com.winning.monitor.agent.logging.message.Caller;
import com.winning.monitor.data.api.ILogin;
import com.winning.monitor.data.api.ITransactionDataQueryService;
import com.winning.monitor.data.api.transaction.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Autowired
    private ILogin login;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat hourFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    private long hour = 3600 * 1000L;
    private long day = 24 * 3600 * 1000L;


    @Test
    public void testQueryDomains() {
        LinkedHashSet<String> set = transactionDataQuery.getAllServerAppNames("BI");
        System.out.println(set);
    }

    @Test
    public void testQueryLogin() {
        LoginMessage set = login.login("admin", "1234");
        System.out.println(set);
    }


    @Test
    public void testTodayAndYestodaySize() {
        ServerCount set = transactionDataQuery.getTodayAndYestodaySize();
        Assert.assertNotNull(set);
    }

    @Test
    public void testTodayAndYestodayWrongSize() {
        ServerCount set = transactionDataQuery.getTodayAndYestodayWrongSize();
        Assert.assertNotNull(set);
    }

    @Test
    public void testTodaySizeByClientType() {
        ServerCountWithType set = transactionDataQuery.getTodaySizeByClientType();
        Assert.assertNotNull(set);
    }

    @Test
    public void testTodaySizeByDomain() {
        ServerCountWithDomainList set = transactionDataQuery.getTodaySizeByDomain();
        Assert.assertNotNull(set);
    }

    @Test
    public void testLastHourWrongMessageList() {
        WrongMessageList set = transactionDataQuery.getLastHourWrongMessageList();
        Assert.assertNotNull(set);
    }

    @Test
    public void testQueryUsrs() {
        LoginMessage set = login.login("admin", "123");
        Assert.assertNotNull(set);
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
        LinkedHashSet<String> set = transactionDataQuery.getAllServerIpAddress("BI", "test1");
        System.out.println(set);
    }


    @Test
    public void testQueryLastHourTransactionTypeReportByServer() {
        TransactionStatisticReport report = transactionDataQuery.queryLastHourTransactionTypeReportByServer("BI", "test1");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryHourTransactionTypeReportByServer() {
        TransactionStatisticReport report = transactionDataQuery.queryHourTransactionTypeReportByServer("BI", "test-group", "2016-11-07 10:00:00");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryDayTransactionTypeReportByServer() {
        TransactionStatisticReport report = transactionDataQuery.queryDayTransactionTypeReportByServer("BI", "test-group", "2016-11-07");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryWeekTransactionTypeReportByServer() {
        TransactionStatisticReport report = transactionDataQuery.queryWeekTransactionTypeReportByServer("BI", "test1", "2016-10-24");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryMonthTransactionTypeReportByServer() {
        TransactionStatisticReport report = transactionDataQuery.queryMonthTransactionTypeReportByServer("BI", "test1", "2016-10-01");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryTodayTransactionTypeReportByServer() {
        TransactionStatisticReport report = transactionDataQuery.queryTodayTransactionTypeReportByServer("BI", "test1");
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
    public void testQueryHourTransactionTypeReportByClient() {
        TransactionStatisticReport report =
                transactionDataQuery.queryHourTransactionTypeReportByClient("BI", "test1", "2016-10-31 15:00:00", "挂号", "");
        Assert.assertNotNull(report);
        report =
                transactionDataQuery.queryHourTransactionTypeReportByClient("BI", "test1", "2016-10-31 15:00:00", "收费", "10.0.0.15");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryTodayTransactionTypeReportByClient() {
        TransactionStatisticReport report =
                transactionDataQuery.queryTodayTransactionTypeReportByClient("BI", "test1", "挂号", "");
        Assert.assertNotNull(report);
        report =
                transactionDataQuery.queryTodayTransactionTypeReportByClient("BI", "test1", "收费", "10.0.0.15");
        Assert.assertNotNull(report);
    }


    @Test
    public void testQueryDayTransactionTypeReportByClient() {
        TransactionStatisticReport report =
                transactionDataQuery.queryDayTransactionTypeReportByClient("BI", "test1", "2016-10-31", "挂号", "");
        Assert.assertNotNull(report);
        report =
                transactionDataQuery.queryDayTransactionTypeReportByClient("BI", "test1", "2016-10-31", "收费", "10.0.0.15");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryWeekTransactionTypeReportByClient() {
        TransactionStatisticReport report =
                transactionDataQuery.queryWeekTransactionTypeReportByClient("BI", "test1", "2016-10-24", "挂号", "");
        Assert.assertNotNull(report);
        report =
                transactionDataQuery.queryWeekTransactionTypeReportByClient("BI", "test1", "2016-10-24", "收费", "10.0.0.15");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryMonthTransactionTypeReportByClient() {
        TransactionStatisticReport report =
                transactionDataQuery.queryMonthTransactionTypeReportByClient("BI", "test1", "2016-10-01", "挂号", "");
        Assert.assertNotNull(report);
        report =
                transactionDataQuery.queryMonthTransactionTypeReportByClient("BI", "test1", "2016-10-01", "收费", "10.0.0.15");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryLastHourTransactionTypeCallTimesReportByServer() {
        TransactionCallTimesReport report =
                transactionDataQuery.queryLastHourTransactionTypeCallTimesReportByServer("BI", "test-group", "门诊收费服务", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryHourTransactionTypeCallTimesReportByServer() {
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
                transactionDataQuery.queryDayTransactionTypeCallTimesReportByServer("BI", "test1", "2016-11-02", "门诊收费服务", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryWeekTransactionTypeCallTimesReportByServer() {
        TransactionCallTimesReport report =
                transactionDataQuery.queryWeekTransactionTypeCallTimesReportByServer("BI", "test-group", "2016-10-31", "门诊收费服务", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryMonthTransactionTypeCallTimesReportByServer() {
        TransactionCallTimesReport report =
                transactionDataQuery.queryMonthTransactionTypeCallTimesReportByServer("BI", "test1", "2016-10-01", "挂号", "");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryLastHourTransactionNameReportByServer() {
        TransactionStatisticReport report =
                transactionDataQuery.queryLastHourTransactionNameReportByServer("BI", "test1", "挂号", "", "住院系统");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryHourTransactionNameReportByServer() {
        TransactionStatisticReport report =
                transactionDataQuery.queryHourTransactionNameReportByServer("BI", "test1", "2016-10-31 15:00:00", "挂号", "", "住院系统");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryTodayTransactionNameReportByServer() {
        TransactionStatisticReport report =
                transactionDataQuery.queryTodayTransactionNameReportByServer("BI", "test1", "挂号", "", "住院系统");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryDayTransactionNameReportByServer() {
        TransactionStatisticReport report =
                transactionDataQuery.queryDayTransactionNameReportByServer("BI", "test-group", "2016-11-04", "门诊收费服务", "", "住院系统");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryWeekTransactionNameReportByServer() {
        TransactionStatisticReport report =
                transactionDataQuery.queryWeekTransactionNameReportByServer("BI", "test1", "2016-10-24", "挂号", "", "住院系统");
        Assert.assertNotNull(report);
    }

    @Test
    public void testQueryMonthTransactionNameReportByServer() {
        TransactionStatisticReport report =
                transactionDataQuery.queryMonthTransactionNameReportByServer("BI", "test1", "2016-10-01", "挂号", "", "住院系统");
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
                transactionDataQuery.queryHourTransactionMessageList("BI", "test-group", "2016-11-07 14:00:00", "门诊收费服务", "", "", "", "", "成功", 0, 100, order);

        Assert.assertNotNull(transactionMessageList);
    }

    @Test
    public void testQueryTodayTransactionMessageList() {

        LinkedHashMap<String, String> order = new LinkedHashMap<>();
        order.put("time", "ASC");

        TransactionMessageList transactionMessageList =
                transactionDataQuery.queryTodayTransactionMessageList("BI", "test-group", "门诊收费服务", "", "", "", "", "成功", 0, 100, order);

        Assert.assertNotNull(transactionMessageList);
    }

    @Test
    public void testQueryDayTransactionMessageList() {

        LinkedHashMap<String, String> order = new LinkedHashMap<>();
        order.put("time", "ASC");

        TransactionMessageList transactionMessageList =
                transactionDataQuery.queryDayTransactionMessageList("BI", "WinMicroService", "2016-11-24", "数据服务", "", "", "", "", "成功", 0, 100, order);

        Assert.assertNotNull(transactionMessageList);
    }

    @Test
    public void testQueryWeekTransactionMessageList() {

        LinkedHashMap<String, String> order = new LinkedHashMap<>();
        order.put("time", "ASC");

        TransactionMessageList transactionMessageList =
                transactionDataQuery.queryWeekTransactionMessageList("BI", "WinMicroService", "2016-10-31", "门诊收费服务", "", "", "", "", "", 0, 100, order);

        Assert.assertNotNull(transactionMessageList);


        TransactionStatisticReport report = transactionDataQuery.queryWeekTransactionTypeReportByServer("BI", "test-group", "2016-10-31");
        Assert.assertNotNull(report);

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
    public void testTransactionMessageListDetails() {
        TransactionMessageListDetail str = transactionDataQuery.queryTransactionMessageListDetails("BI","1f50a7b2-dd97-4c12-96fd-be4be7e5c856",0,"WinEMPI");
        Assert.assertNotNull(str);
    }

    @Test
    public void testComputerHour() {
        int hour = Integer.parseInt("2016-01-01 20:00:00".substring(11, 13));
        Assert.assertTrue(hour == 20);
    }

    @Test
    public void testCompareAllHours() throws ParseException {

        Date period = hourFormat.parse("2016-11-04 00:00:00");
        Date end = hourFormat.parse("2016-11-05 00:00:00");

        String group = "BI";
        String domain = "test-group";
        String typeName = "门诊收费服务";

        long total = 0;

        while (true) {
            if (period.getTime() >= end.getTime())
                return;

            String date = hourFormat.format(period);
            //获取明细
            TransactionMessageList transactionMessageList =
                    transactionDataQuery.queryHourTransactionMessageList(group, domain, date, typeName,
                            "", "", "", "", "", 0, 100, null);
            Assert.assertNotNull(transactionMessageList);

            TransactionStatisticReport dailyReport =
                    transactionDataQuery.queryHourTransactionTypeReportByServer(group, domain, date);

            for (TransactionStatisticData transactionStatisticData : dailyReport.getTransactionStatisticDatas()) {
                if (transactionStatisticData.getTransactionTypeName().equals(typeName)) {
                    if (transactionStatisticData.getTotalCount() != transactionMessageList.getTotalSize()) {
                        Assert.fail(date + "的明细数据发生错误");
                    }
                }
            }

            total = total + transactionMessageList.getTotalSize();
            period = new Date(period.getTime() + hour);
        }
    }


    @Test
    public void testCompareAllDays() throws ParseException {

        Date period = dateFormat.parse("2016-10-31");
        Date end = dateFormat.parse("2016-11-07");

        String group = "BI";
        String domain = "test-group";
        String typeName = "门诊收费服务";

        long total = 0;

        while (true) {
            if (period.getTime() >= end.getTime())
                break;

            String date = dateFormat.format(period);
            //获取明细
            TransactionMessageList transactionMessageList =
                    transactionDataQuery.queryDayTransactionMessageList(group, domain, date, typeName,
                            "", "", "", "", "", 0, 100, null);
            Assert.assertNotNull(transactionMessageList);

            TransactionStatisticReport dailyReport =
                    transactionDataQuery.queryDayTransactionTypeReportByServer(group, domain, date);

            for (TransactionStatisticData transactionStatisticData : dailyReport.getTransactionStatisticDatas()) {
                if (transactionStatisticData.getTransactionTypeName().equals(typeName)) {
                    if (transactionStatisticData.getTotalCount() != transactionMessageList.getTotalSize()) {
                        Assert.fail(date + "的明细数据发生错误");
                    }
                }
            }

            total = total + transactionMessageList.getTotalSize();
            period = new Date(period.getTime() + day);
        }

        TransactionStatisticReport weekReport =
                transactionDataQuery.queryWeekTransactionTypeReportByServer(group, domain, "2016-10-31");

        for (TransactionStatisticData transactionStatisticData : weekReport.getTransactionStatisticDatas()) {
            if (transactionStatisticData.getTransactionTypeName().equals(typeName)) {
                Assert.assertTrue(total == transactionStatisticData.getTotalCount());
            }
        }

    }


}
