package com.winning.monitor.data.storage.mongodb;

import com.winning.monitor.data.api.transaction.vo.TransactionReportVO;
import com.winning.monitor.data.api.transaction.vo.TransactionTypeVO;
import com.winning.monitor.data.storage.api.ITransactionDataStorage;
import com.winning.monitor.data.storage.api.MessageTreeStorage;
import com.winning.monitor.data.storage.api.entity.MessageTreeList;
import com.winning.monitor.data.storage.mongodb.po.transaction.TransactionMachinePO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by nicholasyan on 16/9/18.
 */
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*-context.xml"})
public class TransactionStorageUT extends
        AbstractJUnit4SpringContextTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ITransactionDataStorage transactionDataStorage;

    @Autowired
    private MessageTreeStorage messageTreeStorage;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testSaveMachine() {
        TransactionMachinePO transactionMachine = new TransactionMachinePO();
        //transactionMachine.setIp("123");
        TransactionTypeVO transactionTypeVO = new TransactionTypeVO();
        transactionTypeVO.setId("test");
        //transactionMachine.addTransactionType(transactionTypeVO);
        this.mongoTemplate.save(transactionMachine, "transactionReports");
    }

    @Test
    public void testQueryTransactionReports() {
        List<TransactionReportVO> list =
                transactionDataStorage.queryRealtimeTransactionReports
                        ("microservice", "2016-09-18 13:00:00");

        Assert.assertNotNull(list);
    }

    @Test
    public void testQueryPerson() {
        Criteria criteria = Criteria.where("name").lte("");
        Query query = new Query(criteria);
        this.mongoTemplate.find(query, Person.class, "PERSON");
    }

    @Test
    public void testQueryTransactionType() {
        Criteria criteria = Criteria.where("name").lte("");
        Query query = new Query(criteria);
        this.mongoTemplate.find(query, Person.class, "PERSON");
    }

    @Test
    public void testQueryRealtimeTransactionReports() {
        Map<String, Object> map = new HashMap<>();
        map.put("domain", "test1");
        map.put("startTime", "2016-10-24 20:00:00");
        map.put("transactionType", "挂号");

        List<TransactionReportVO> list =
                transactionDataStorage.queryRealtimeTransactionReports
                        (map);

        Assert.assertNotNull(list);
    }

    @Test
    public void testQueryIps() {
        Set<String> list =
                transactionDataStorage.findAllServerIpAddress("test1");

        Assert.assertNotNull(list);
    }

    @Test
    public void testQueryMessageTree() throws ParseException {
        Date start = this.simpleDateFormat.parse("2016-10-01 00:00:00");
        Date end = this.simpleDateFormat.parse("2016-10-25 23:00:00");

        LinkedHashMap<String, String> order = new LinkedHashMap<>();
        order.put("time", "ASC");

        MessageTreeList list =
                messageTreeStorage.queryMessageTree("test2", start.getTime(), end.getTime(),
                        null, 0, 1, order);

        System.out.println();

        System.out.println("共" + list.getTotalSize());
        System.out.println("0:" + list.getMessageTrees().get(0).getMessage().getTimestamp());

        for (int i = 1; i < list.getTotalSize(); i++) {
            list =
                    messageTreeStorage.queryMessageTree("test2", start.getTime(), end.getTime(),
                            null, i, 1, order);
            System.out.println(i + ":" + list.getMessageTrees().get(0).getMessage().getTimestamp());
        }

        Assert.assertNotNull(list);
    }


}
