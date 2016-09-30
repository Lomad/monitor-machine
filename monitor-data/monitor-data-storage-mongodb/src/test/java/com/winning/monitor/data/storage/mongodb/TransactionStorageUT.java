package com.winning.monitor.data.storage.mongodb;

import com.winning.monitor.data.api.transaction.vo.TransactionReportType;
import com.winning.monitor.data.api.transaction.vo.TransactionReportVO;
import com.winning.monitor.data.api.transaction.vo.TransactionTypeVO;
import com.winning.monitor.data.storage.api.ITransactionDataStorage;
import com.winning.monitor.data.storage.mongodb.po.transaction.TransactionMachinePO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.List;

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
                transactionDataStorage.queryTransactionReports
                        ("microservice", "2016-09-18 13:00:00", TransactionReportType.REALTIME);

        Assert.assertNotNull(list);
    }

    @Test
    public void testQueryPerson() {
        Criteria criteria = Criteria.where("name").lte("");
        Query query = new Query(criteria);
        this.mongoTemplate.find(query, Person.class, "PERSON");
    }


}
