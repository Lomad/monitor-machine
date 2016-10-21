package com.winning.monitor.data.transaction.test;

import com.winning.monitor.data.api.ITransactionDataQueryService;
import com.winning.monitor.data.api.transaction.domain.TransactionStatisticReport;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

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
    public void testQueryLastHourTransactionTypeReportByServer() {
        TransactionStatisticReport report = transactionDataQuery.queryLastHourTransactionTypeReportByServer("test1");
        Assert.assertNotNull(report);
    }

}
