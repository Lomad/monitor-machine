package com.winning.monitor.data.storage.mongodb;

import com.winning.monitor.data.api.transaction.vo.TransactionReportType;
import com.winning.monitor.data.api.transaction.vo.TransactionReportVO;
import com.winning.monitor.data.storage.api.ITransactionDataStorage;
import com.winning.monitor.data.storage.api.exception.StorageException;
import com.winning.monitor.data.storage.mongodb.po.transaction.TransactionReportPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by nicholasyan on 16/9/14.
 */
public class TransactionDataStorage implements ITransactionDataStorage {

    private static final Logger logger = LoggerFactory.getLogger(TransactionDataStorage.class);
    private static final String REALTIME_COLLECTION_NAME = "TransactionRealtimeReports";

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void checkConnect() {
        logger.info("正在检测与mongodb之间的连接,address={}", mongoTemplate.getDb().getMongo().getAddress());
        try {
            this.mongoTemplate.getDb().getStats();
        } catch (Exception e) {
            logger.error("连接mongodb时发生错误{}", e.getMessage(), e);
            throw e;
        }
        logger.info("与mongodb之间的连接成功!");
    }


    @Override
    public LinkedHashSet<String> findAllTransactionDomains() {
        List<String> domains = this.mongoTemplate.getCollection(REALTIME_COLLECTION_NAME).distinct("domain");
        Collections.sort(domains);
        return new LinkedHashSet<>(domains);
    }

    @Override
    public List<TransactionReportVO> queryRealtimeTransactionReports(String domain, String startTime) {
        Query query = new Query();
        query.addCriteria(new Criteria("domain").is(domain));
        query.addCriteria(new Criteria("startTime").is(startTime));
        query.addCriteria(new Criteria("type").is(TransactionReportType.REALTIME.getName()));

        List<TransactionReportVO> list =
                this.mongoTemplate.find(query, TransactionReportVO.class, REALTIME_COLLECTION_NAME);

        if (list != null) {
            for (TransactionReportVO report : list)
                report.initialReport();
        }

        return list;
    }


    @Override
    public List<TransactionReportVO> queryRealtimeTransactionReports(String domain, String startTime, String endTime) {
        Criteria criteria = Criteria.where("domain").is(domain)
                .and("type").is(TransactionReportType.REALTIME.getName())
                .and("startTime").gte(startTime)
                .and("endTime").lt(endTime);
        Query query = new Query(criteria);

        List<TransactionReportPO> list =
                this.mongoTemplate.find(query, TransactionReportPO.class, REALTIME_COLLECTION_NAME);

        List<TransactionReportVO> transactionReports = this.convertTransactionReports(list);

        return transactionReports;
    }

    @Override
    public List<TransactionReportVO> queryHistoryTransactionReports(String domain, String startTime, String endTime, TransactionReportType type) {
        Criteria criteria = Criteria.where("domain").is(domain)
                .and("type").is(type.getName())
                .and("startTime").gte(startTime).lt(endTime);
        Query query = new Query(criteria);

        String collectionName = this.getHistoryCollectionName(type);

        List<TransactionReportPO> list =
                this.mongoTemplate.find(query, TransactionReportPO.class, collectionName);

        List<TransactionReportVO> transactionReports = this.convertTransactionReports(list);

        return transactionReports;
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
                this.mongoTemplate.find(query, TransactionReportVO.class, REALTIME_COLLECTION_NAME);


        if (list != null) {
            for (TransactionReportVO report : list)
                report.initialReport();
        }

        return list;
    }


    @Override
    public void storeRealtimeTransactionReport(TransactionReportVO transactionReport) throws StorageException {
        try {
            TransactionReportPO transactionReportPO = new TransactionReportPO(transactionReport);

            Query query = new Query();
            query.addCriteria(new Criteria("_id").is(transactionReport.getId()));

            boolean existsReport = this.mongoTemplate.exists(query, TransactionReportPO.class, REALTIME_COLLECTION_NAME);

            //如果不存在,则直接插入数据
            if (existsReport == false) {
                this.mongoTemplate.insert(transactionReportPO, REALTIME_COLLECTION_NAME);
                return;
            }

            //更新数据
            Update update = new Update();

            update.set("machines", transactionReportPO.getMachines());
            this.mongoTemplate.upsert(query, update, REALTIME_COLLECTION_NAME);
        } catch (Exception e) {
            logger.error("保存至mongodb时执行storeTransactionReport发生错误", e);
        }
    }

    @Override
    public void storeHistoryTransactionReport(TransactionReportVO transactionReport) {
        try {
            TransactionReportPO transactionReportPO = new TransactionReportPO(transactionReport);

            Query query = new Query();
            query.addCriteria(new Criteria("domain").is(transactionReport.getDomain()));
            query.addCriteria(new Criteria("startTime").is(transactionReport.getStartTime()));
            query.addCriteria(new Criteria("endTime").is(transactionReport.getEndTime()));
            query.addCriteria(new Criteria("type").is(transactionReport.getType().getName()));

            String collectionName = this.getHistoryCollectionName(transactionReport.getType());

            boolean existsReport = this.mongoTemplate.exists(query, TransactionReportPO.class, collectionName);

            //如果不存在,则直接插入数据
            if (existsReport == false) {
                transactionReportPO.setId(UUID.randomUUID().toString());
                this.mongoTemplate.insert(transactionReportPO, collectionName);
                return;
            }

            //更新数据
            Update update = new Update();
            update.set("machines", transactionReportPO.getMachines());
            this.mongoTemplate.upsert(query, update, collectionName);
        } catch (Exception e) {
            logger.error("保存至mongodb时执行storeTransactionReport发生错误", e);
        }
    }


    private List<TransactionReportVO> convertTransactionReports(List<TransactionReportPO> reports) {
        List<TransactionReportVO> transactionReports = new ArrayList<>();
        if (reports == null || reports.size() == 0)
            return transactionReports;
        for (TransactionReportPO transactionReportPO : reports) {
            TransactionReportVO transactionReportVO = transactionReportPO.toTransactionReportVO();
            transactionReports.add(transactionReportVO);
        }
        return transactionReports;
    }

    private List<TransactionReportVO> convertToTransactionReports(List<LinkedHashMap> reportMaps) {
        List<TransactionReportVO> transactionReports = new ArrayList<>();
        if (reportMaps == null || reportMaps.size() == 0)
            return transactionReports;

        for (LinkedHashMap<String, Object> reportMap : reportMaps)
            transactionReports.add(this.convertTransactionReport(reportMap));

        return transactionReports;
    }

    private TransactionReportVO convertTransactionReport(LinkedHashMap<String, Object> reportMap) {
        TransactionReportVO transactionReportVO = new TransactionReportVO();
        transactionReportVO.setId(ConvertUtils.getStringValue(reportMap.get("id")));
        transactionReportVO.setDomain(ConvertUtils.getStringValue(reportMap.get("domain")));
        transactionReportVO.setIndex(ConvertUtils.getIntValue(reportMap.get("idx")));
        transactionReportVO.setIps(ConvertUtils.getStringSetValue(reportMap.get("ips")));
        transactionReportVO.setType(TransactionReportType.valueOf(ConvertUtils.getStringValue(reportMap.get("type"))));
        transactionReportVO.setServer(ConvertUtils.getStringValue(reportMap.get("server")));
        transactionReportVO.setStartTime(ConvertUtils.getStringValue(reportMap.get("startTime")));
        transactionReportVO.setEndTime(ConvertUtils.getStringValue(reportMap.get("endTime")));

        return transactionReportVO;
    }

    private String getHistoryCollectionName(TransactionReportType transactionReport) {
        String collectionName = "";
        switch (transactionReport) {
            case HOURLY:
                collectionName = "TransactionHourlyReports";
                break;
            case DAILY:
                collectionName = "TransactionDailyReports";
                break;
            case WEEKLY:
                collectionName = "TransactionWeeklyReports";
                break;
            case MONTHLY:
                collectionName = "TransactionMonthlyReports";
                break;
            default:
                collectionName = "TransactionHourlyReports";
        }
        return collectionName;
    }


}
