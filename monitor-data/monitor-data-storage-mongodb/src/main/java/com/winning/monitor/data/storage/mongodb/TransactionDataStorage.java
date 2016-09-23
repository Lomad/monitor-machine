package com.winning.monitor.data.storage.mongodb;

import com.winning.monitor.data.api.transaction.vo.TransactionMachineVO;
import com.winning.monitor.data.api.transaction.vo.TransactionReportType;
import com.winning.monitor.data.api.transaction.vo.TransactionReportVO;
import com.winning.monitor.data.storage.api.ITransactionDataStorage;
import com.winning.monitor.data.storage.api.exception.StorageException;
import com.winning.monitor.data.storage.mongodb.po.transaction.TransactionMachinePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicholasyan on 16/9/14.
 */
public class TransactionDataStorage implements ITransactionDataStorage {

    private static final String COLLECTION_NAME = "transactionReports";
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<TransactionReportVO> queryTransactionReports(String domain, String startTime, TransactionReportType type) {
        Query query = new Query();
        query.addCriteria(new Criteria("domain").is(domain));
        query.addCriteria(new Criteria("startTime").is(startTime));
        query.addCriteria(new Criteria("type").is(type));

        List<TransactionReportVO> list =
                this.mongoTemplate.find(query, TransactionReportVO.class, COLLECTION_NAME);

        if (list != null) {
            for (TransactionReportVO report : list)
                report.initialReport();
        }

        return list;
    }

    @Override
    public List<TransactionReportVO> queryTransactionReportsByType(String domain, String startTime, String typeName, TransactionReportType type) {
        Query query = new Query();
        query.fields().include("machines.transactionTypes.transactionNames");
        query.addCriteria(new Criteria("domain").is(domain));
        query.addCriteria(new Criteria("startTime").is(startTime));
        query.addCriteria(new Criteria("machines.transactionTypes.name").is(typeName));
        query.addCriteria(new Criteria("type").is(type));

        List<TransactionReportVO> list =
                this.mongoTemplate.find(query, TransactionReportVO.class, COLLECTION_NAME);


        if (list != null) {
            for (TransactionReportVO report : list)
                report.initialReport();
        }

        return list;
    }

    @Override
    public void storeTransactionReport(TransactionReportVO transactionReport) throws StorageException {

        Query query = new Query();
        query.addCriteria(new Criteria("id").is(transactionReport.getId()));
        Update update = new Update();

        update.set("id", transactionReport.getId());
        update.set("domain", transactionReport.getDomain());
        update.set("idx", transactionReport.getIndex());
        update.set("ips", transactionReport.getIps());
        update.set("type", transactionReport.getType());
        update.set("server", transactionReport.getServer());
        update.set("startTime", transactionReport.getStartTime());
        update.set("endTime", transactionReport.getEndTime());

        List<TransactionMachinePO> machines = new ArrayList<>();
        if (transactionReport.getMachines() != null) {
            for (TransactionMachineVO machineVO : transactionReport.getMachines()) {
                machines.add(new TransactionMachinePO(machineVO));
            }
        }
        update.set("machines", machines);

        this.mongoTemplate.upsert(query, update, COLLECTION_NAME);
    }
}
