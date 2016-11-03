package com.winning.monitor.website.test;

import com.winning.monitor.data.api.ITransactionDataQueryService;
import com.winning.monitor.data.api.transaction.domain.TransactionStatisticReport;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.LinkedHashSet;

/**
 * Created by Admin on 2016/10/21.
 */
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*.xml"})
public class TransactionQueryUT  extends
        org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests {

    @Autowired
    private ITransactionDataQueryService transactionDataQuery;
    private String GroupId = "BI";
    @Test
    public void testQueryAllDomain(){
        LinkedHashSet<String> set = transactionDataQuery.getAllServerAppNames(GroupId);
        Assert.assertNotNull(set);
    }

    @Test
    public void testQuery(){
        TransactionStatisticReport report = transactionDataQuery.queryLastHourTransactionTypeReportByServer(GroupId,"test1");
        Assert.assertNotNull(report);
    }


}
