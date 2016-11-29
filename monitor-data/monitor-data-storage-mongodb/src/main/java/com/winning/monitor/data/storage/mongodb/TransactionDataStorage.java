package com.winning.monitor.data.storage.mongodb;

import com.winning.monitor.data.api.transaction.vo.*;
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
    public  List<UsersVO> findUsers(String username, String password) {
        Query query = new Query();
        query.addCriteria(new Criteria("username").is(username));
        query.addCriteria(new Criteria("password").is(password));
        List<UsersVO> users = this.mongoTemplate.find(query, UsersVO.class,"Admins");
        return users;
    }

    @Override
    public SumVO findAllserverSize(String startTime, String endTime, Map<String, Object> map) {
        long failSum = 0;
        long totalSum = 0;
        Query query = new Query();
        query.addCriteria(new Criteria("startTime").gte(startTime).lte(endTime));
        if (map != null && map.containsKey("severAppName"))
            query.addCriteria(new Criteria("domain").is(map.get("severAppName")));
        if (map != null && map.containsKey("clientType"))
            query.addCriteria(new Criteria("machines.transactionClients.type").is(map.get("clientType")));

        List<TransactionReportVO> list =
                this.mongoTemplate.find(query, TransactionReportVO.class, REALTIME_COLLECTION_NAME);
        List<TransactionMachineVO> machineVOs = new ArrayList<TransactionMachineVO>();
        List<TransactionClientVO> clientVOs = new ArrayList<TransactionClientVO>();
        List<TransactionTypeVO> typeVOs = new ArrayList<TransactionTypeVO>();
        if (list != null) {
            for (TransactionReportVO report : list) {
                //report.initialReport();
                machineVOs = report.getMachines();
                for (TransactionMachineVO machs : machineVOs) {
                    clientVOs = machs.getTransactionClients();
                    for (TransactionClientVO clients : clientVOs){
                        typeVOs = clients.getTransactionTypes();
                        for(TransactionTypeVO types: typeVOs){
                            long failCount = types.getFailCount();
                            failSum += failCount;
                            long tatalCount = types.getTotalCount();
                            totalSum += tatalCount;
                        }

                    }
                }
            }
        }
        SumVO sumVOs = new SumVO();
        sumVOs.setFailSum(failSum);
        sumVOs.setTotalSum(totalSum);

        return sumVOs ;
    }

    @Override
    public List<String> findAllTransactionTypes() {
        List<String> transactionTypes = this.mongoTemplate.getCollection(REALTIME_COLLECTION_NAME).distinct("machines.transactionClients.type");
        Collections.sort(transactionTypes);
        return transactionTypes;
    }


    @Override
    public LinkedHashSet<String> findAllTransactionDomains(String group) {
        Query query = new Query();
        query.addCriteria(new Criteria("group").is(group));
        List<String> domains = this.mongoTemplate.getCollection(REALTIME_COLLECTION_NAME).distinct("domain");
        Collections.sort(domains);
        return new LinkedHashSet<>(domains);
    }

    @Override
    public LinkedHashSet<String> findAllTransactionClients(String group) {
        Query query = new Query();
        query.addCriteria(new Criteria("group").is(group));
        List<String> clients = this.mongoTemplate.getCollection(REALTIME_COLLECTION_NAME).distinct("machines.transactionClients.domain");
        clients.remove("");
        Collections.sort(clients);
        return new LinkedHashSet<>(clients);
    }

    @Override
    public LinkedHashSet<String> findAllServerIpAddress(String group, String domain) {
        Criteria criteria = Criteria.where("domain").is(domain)
                           .and("group").is(group);
        Query query = new Query(criteria);
        List<String> ips = this.mongoTemplate.getCollection(REALTIME_COLLECTION_NAME)
                .distinct("machines.ip", query.getQueryObject());
        Collections.sort(ips);
        return new LinkedHashSet<>(ips);
    }

    @Override
    public List<TransactionReportVO> queryRealtimeTransactionReports(String group, String domain, String startTime) {
        Query query = new Query();
        query.addCriteria(new Criteria("domain").is(domain));
        query.addCriteria(new Criteria("startTime").is(startTime));
        query.addCriteria(new Criteria("type").is(TransactionReportType.REALTIME.getName()));
        query.addCriteria(new Criteria("group").is(group));
        List<TransactionReportVO> list =
                this.mongoTemplate.find(query, TransactionReportVO.class, REALTIME_COLLECTION_NAME);

        if (list != null) {
            for (TransactionReportVO report : list)
                report.initialReport();
        }

        return list;
    }

    @Override
    public List<TransactionReportVO> queryRealtimeClientTransactionReports(String group, String domain, String startTime) {
        Query query = new Query();
        query.addCriteria(new Criteria("machines.transactionClients.domain").is(domain));
        query.addCriteria(new Criteria("startTime").is(startTime));
        query.addCriteria(new Criteria("type").is(TransactionReportType.REALTIME.getName()));
        query.addCriteria(new Criteria("group").is(group));
        List<TransactionReportVO> list =
                this.mongoTemplate.find(query, TransactionReportVO.class, REALTIME_COLLECTION_NAME);

        if (list != null) {
            for (TransactionReportVO report : list)
                report.initialReport();
        }

        return list;
    }

    @Override
    public List<TransactionReportVO> queryRealtimeTransactionReports(String group, Map<String, Object> map) {
        Query query = new Query();
        query.addCriteria(new Criteria("type").is(TransactionReportType.REALTIME.getName()));
        query.addCriteria(new Criteria("group").is(group));
        if (map != null && map.containsKey("domain"))
            query.addCriteria(new Criteria("domain").is(map.get("domain")));
        if (map != null && map.containsKey("startTime"))
            query.addCriteria(new Criteria("startTime").is(map.get("startTime")));
        if (map != null && map.containsKey("transactionType"))
            query.addCriteria(new Criteria("machines.transactionClients.transactionTypes.name").is(map.get("transactionType")));
        if (map != null && map.containsKey("serverIp"))
            query.addCriteria(new Criteria("machines.ip").is(map.get("serverIp")));
        if (map != null && map.containsKey("clientAppName"))
            query.addCriteria(new Criteria("machines.transactionClients.domain").is(map.get("clientAppName")));


        List<TransactionReportVO> list =
                this.mongoTemplate.find(query, TransactionReportVO.class, REALTIME_COLLECTION_NAME);

        if (list != null) {
            for (TransactionReportVO report : list)
                report.initialReport();
        }

        return list;
    }

    @Override
    public List<TransactionReportVO> queryRealtimeClientTransactionReports(String group, Map<String, Object> map) {
        Query query = new Query();
        query.addCriteria(new Criteria("type").is(TransactionReportType.REALTIME.getName()));
        query.addCriteria(new Criteria("group").is(group));
        if (map != null && map.containsKey("domain"))
            query.addCriteria(new Criteria("domain").is(map.get("domain")));
        if (map != null && map.containsKey("startTime"))
            query.addCriteria(new Criteria("startTime").is(map.get("startTime")));
        if (map != null && map.containsKey("transactionType"))
            query.addCriteria(new Criteria("machines.transactionClients.transactionTypes.name").is(map.get("transactionType")));
        if (map != null && map.containsKey("serverIp"))
            query.addCriteria(new Criteria("machines.ip").is(map.get("serverIp")));
        if (map != null && map.containsKey("clientAppName"))
            query.addCriteria(new Criteria("machines.transactionClients.domain").is(map.get("clientAppName")));


        List<TransactionReportVO> list =
                this.mongoTemplate.find(query, TransactionReportVO.class, REALTIME_COLLECTION_NAME);

        if (list != null) {
            for (TransactionReportVO report : list)
                report.initialReport();
        }

        return list;
    }


    @Override
    public List<TransactionReportVO> queryRealtimeTransactionReports(String group, String domain, String startTime, String endTime, Map<String, Object> map) {
        Query query = new Query();
        query.addCriteria(new Criteria("domain").is(domain));
        query.addCriteria(new Criteria("startTime").gte(startTime).lte(endTime));
        query.addCriteria(new Criteria("type").is(TransactionReportType.REALTIME.getName()));
        query.addCriteria(new Criteria("group").is(group));


        if (map != null && map.containsKey("transactionType"))
            query.addCriteria(new Criteria("machines.transactionClients.transactionTypes.name").is(map.get("transactionType")));
        if (map != null && map.containsKey("serverIp"))
            query.addCriteria(new Criteria("machines.ip").is(map.get("serverIp")));
        if (map != null && map.containsKey("clientAppName"))
            query.addCriteria(new Criteria("machines.transactionClients.domain").is(map.get("clientAppName")));

        List<TransactionReportVO> list =
                this.mongoTemplate.find(query, TransactionReportVO.class, REALTIME_COLLECTION_NAME);

        if (list != null) {
            for (TransactionReportVO report : list)
                report.initialReport();
        }

        return list;
    }

    @Override
    public List<TransactionReportVO> queryRealtimeClientTransactionReports(String group, String domain, String startTime, String endTime, Map<String, Object> map) {
        Query query = new Query();
        query.addCriteria(new Criteria("machines.transactionClients.domain").is(domain));
        query.addCriteria(new Criteria("startTime").gte(startTime).lte(endTime));
        query.addCriteria(new Criteria("type").is(TransactionReportType.REALTIME.getName()));
        query.addCriteria(new Criteria("group").is(group));


        if (map != null && map.containsKey("transactionType"))
            query.addCriteria(new Criteria("machines.transactionClients.transactionTypes.name").is(map.get("transactionType")));

        List<TransactionReportVO> list =
                this.mongoTemplate.find(query, TransactionReportVO.class, REALTIME_COLLECTION_NAME);

        if (list != null) {
            for (TransactionReportVO report : list)
                report.initialReport();
        }

        return list;
    }


    @Override
    public List<TransactionReportVO> queryRealtimeTransactionReports(String group, String domain, String startTime, String endTime) {
        Criteria criteria = Criteria.where("domain").is(domain)
                .and("type").is(TransactionReportType.REALTIME.getName())
                .and("startTime").gte(startTime).lte(endTime)
                .and("group").is(group);
        Query query = new Query(criteria);


        List<TransactionReportPO> list =
                this.mongoTemplate.find(query, TransactionReportPO.class, REALTIME_COLLECTION_NAME);

        List<TransactionReportVO> transactionReports = this.convertTransactionReports(list);

        return transactionReports;
    }

    @Override
    public List<TransactionReportVO> queryRealtimeClientTransactionReports(String group, String domain, String startTime, String endTime) {
        Criteria criteria = Criteria.where("machines.transactionClients.domain").is(domain)
                .and("type").is(TransactionReportType.REALTIME.getName())
                .and("startTime").gte(startTime).lte(endTime)
                .and("group").is(group);
        Query query = new Query(criteria);


        List<TransactionReportPO> list =
                this.mongoTemplate.find(query, TransactionReportPO.class, REALTIME_COLLECTION_NAME);

        List<TransactionReportVO> transactionReports = this.convertTransactionReports(list);

        return transactionReports;
    }


    @Override
    public List<TransactionReportVO> queryHistoryTransactionReports(String group, String domain, String startTime, String endTime, TransactionReportType type) {
        Criteria criteria = Criteria.where("domain").is(domain)
                .and("type").is(type.getName())
                .and("startTime").gte(startTime).lt(endTime)
                .and("group").is(group);
        Query query = new Query(criteria);

        String collectionName = this.getHistoryCollectionName(type);

        List<TransactionReportPO> list =
                this.mongoTemplate.find(query, TransactionReportPO.class, collectionName);

        List<TransactionReportVO> transactionReports = this.convertTransactionReports(list);

        return transactionReports;
    }

    @Override
    public List<TransactionReportVO> queryHistoryClientTransactionReports(String group,
                                                                          String domain,
                                                                          String startTime,
                                                                          String endTime,
                                                                          TransactionReportType type) {
        Criteria criteria = Criteria.where("machines.transactionClients.domain").is(domain)
                .and("type").is(type.getName())
                .and("startTime").gte(startTime).lt(endTime)
                .and("group").is(group);
        Query query = new Query(criteria);

        String collectionName = this.getHistoryCollectionName(type);

        List<TransactionReportPO> list =
                this.mongoTemplate.find(query, TransactionReportPO.class, collectionName);

        List<TransactionReportVO> transactionReports = this.convertTransactionReports(list);

        return transactionReports;
    }


    @Override
    public List<TransactionReportVO> queryHistoryTransactionReports(String group, String domain, String startTime, String endTime,TransactionReportType type,Map<String, Object> map) {
        Query query = new Query();
        query.addCriteria(new Criteria("domain").is(domain));
        query.addCriteria(new Criteria("startTime").gte(startTime).lte(endTime));
        query.addCriteria(new Criteria("type").is(type.getName()));
        query.addCriteria(new Criteria("group").is(group));
        String collectionName = this.getHistoryCollectionName(type);

        if (map != null && map.containsKey("transactionType"))
            query.addCriteria(new Criteria("machines.transactionClients.transactionTypes.name").is(map.get("transactionType")));
        if (map != null && map.containsKey("serverIp"))
            query.addCriteria(new Criteria("machines.ip").is(map.get("serverIp")));
        if (map != null && map.containsKey("clientAppName"))
            query.addCriteria(new Criteria("machines.transactionClients.domain").is(map.get("clientAppName")));

        List<TransactionReportVO> list =
                this.mongoTemplate.find(query, TransactionReportVO.class, collectionName);

        if (list != null) {
            for (TransactionReportVO report : list)
                report.initialReport();
        }

        return list;
    }

    @Override
    public List<TransactionReportVO> queryHistoryClientTransactionReports(String group,
                                                                          String domain,
                                                                          String startTime,
                                                                          String endTime,
                                                                          TransactionReportType type,
                                                                          Map<String, Object> map) {
        Query query = new Query();
        query.addCriteria(new Criteria("machines.transactionClients.domain").is(domain));
        query.addCriteria(new Criteria("startTime").gte(startTime).lte(endTime));
        query.addCriteria(new Criteria("type").is(type.getName()));
        query.addCriteria(new Criteria("group").is(group));
        String collectionName = this.getHistoryCollectionName(type);

        if (map != null && map.containsKey("transactionType"))
            query.addCriteria(new Criteria("machines.transactionClients.transactionTypes.name").is(map.get("transactionType")));



        List<TransactionReportVO> list =
                this.mongoTemplate.find(query, TransactionReportVO.class, collectionName);

        if (list != null) {
            for (TransactionReportVO report : list)
                report.initialReport();
        }

        return list;
    }


    @Override
    public List<TransactionReportVO> queryTransactionReportsByType(String group, String domain, String startTime, String typeName, TransactionReportType type) {
        Query query = new Query();
        query.fields().include("machines.transactionTypes.transactionNames");
        query.addCriteria(new Criteria("domain").is(domain));
        query.addCriteria(new Criteria("group").is(group));
        query.addCriteria(new Criteria("startTime").is(startTime));
        query.addCriteria(new Criteria("machines.transactionTypes.name").is(typeName));
        query.addCriteria(new Criteria("type").is(type));
        query.addCriteria(new Criteria("group").is(group));


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
            query.addCriteria(new Criteria("group").is(transactionReport.getGroup()));
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
        transactionReportVO.setGroup(ConvertUtils.getStringValue(reportMap.get("group")));
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
