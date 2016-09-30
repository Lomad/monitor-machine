package com.winning.monitor.supervisor.consumer.logging.test;

import com.winning.monitor.superisor.consumer.logging.transaction.TransactionReportBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nicholasyan on 16/9/29.
 */
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*.xml"})
public class TransactionReportBuilderUT extends
        org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests {

    @Autowired
    private TransactionReportBuilder transactionReportBuilder;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testMergeReport() throws ParseException {
        Date period = sdf.parse("2016-09-29 16:00:00");
        boolean result = this.transactionReportBuilder.buildHourlyTask
                (TransactionReportBuilder.TASK_BUILDER_NAME, "test1", period);
        Assert.assertTrue(result);
    }


}
