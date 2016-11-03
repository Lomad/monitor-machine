package com.winning.monitor.data.transaction.test;

import com.winning.monitor.data.api.IClientTransactionDataQueryService;
import com.winning.monitor.data.api.ITransactionDataQueryService;
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


}
