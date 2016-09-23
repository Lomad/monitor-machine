package com.winning.monitor.data.transaction.test;

import com.winning.monitor.data.api.ITransactionDataQuery;
import com.winning.monitor.data.api.transaction.vo.TransactionReportType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created by nicholasyan on 16/9/14.
 */
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*-context.xml"})
public class MongodbTransactionQueryUT extends
        AbstractJUnit4SpringContextTests {

    @Autowired
    private ITransactionDataQuery transactionDataQuery;

    @Test
    public void testQuery() {
        transactionDataQuery.queryTransactionReport("test1", "2016-09-14 11:00:00",
                TransactionReportType.REALTIME);

    }

    @Test
    public void testQueryType() {
        transactionDataQuery.queryByTranscationType("application-helloworld", "2016-09-18 16:00:00",
                "rpc",
                TransactionReportType.REALTIME);

    }

}
