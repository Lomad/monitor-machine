package com.winning.monitor.data.storage.mongodb;

import com.winning.monitor.data.api.systemInfo.SystemInfoReportVO;
import com.winning.monitor.data.storage.api.ISystemInfoStorage;
import com.winning.monitor.data.storage.api.exception.StorageException;
import com.winning.monitor.data.storage.mongodb.po.systemInfo.SystemInfoReportPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Lemod
 * @Version 2016/12/7
 */
public class SystemInfoStorage implements ISystemInfoStorage {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfoStorage.class);
    private static final String SYSTEM_INFO_COLLECTION_NAME = "SystemInfoRealTimeReports";

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void checkConnect() {
        logger.info("正在检测与mongodb之间的连接...");
        try {
            this.mongoTemplate.getDb().getStats();
        } catch (Exception e) {
            logger.error("连接mongodb时发生错误{}", e.getMessage(), e);
            throw e;
        }
        logger.info("与mongodb之间的连接成功!");
    }

    @Override
    public void storeCollectDataReport(SystemInfoReportVO infoReportVO) throws StorageException {
        SystemInfoReportPO systemInfoReportPO = new SystemInfoReportPO(infoReportVO);

        try {
            Query query = new Query();
            query.addCriteria(new Criteria("_id").is(infoReportVO.getId()));

            boolean exists = this.mongoTemplate.exists(query,SystemInfoReportPO.class,SYSTEM_INFO_COLLECTION_NAME);

            if (!exists){
                this.mongoTemplate.insert(systemInfoReportPO,SYSTEM_INFO_COLLECTION_NAME);
                return;
            }

            Update update = new Update();
            update.set("infoList",systemInfoReportPO.getInfoList());

            this.mongoTemplate.upsert(query,update,SYSTEM_INFO_COLLECTION_NAME);
        }catch (Exception e){
            logger.error("更新数据库失败"+e.getMessage());
        }
    }

    @Override
    public List<SystemInfoReportVO> queryRealTimeInfo(String ipAddress,String startTime) {

        List<SystemInfoReportVO> systemInfoReportVOList = new ArrayList<>();
        try {
            Query query = new Query();
            query.addCriteria(new Criteria("ipAddress").is(ipAddress));
            query.addCriteria(new Criteria("startTime").is(startTime));
            List<SystemInfoReportPO> systemInfoReportPOList;

            systemInfoReportPOList = this.mongoTemplate.find(query,SystemInfoReportPO.class,SYSTEM_INFO_COLLECTION_NAME);

            if (systemInfoReportPOList != null){
                for (SystemInfoReportPO systemInfoReportPO : systemInfoReportPOList){
                    SystemInfoReportVO systemInfoReportVO = systemInfoReportPO.toReportVO();
                    systemInfoReportVOList.add(systemInfoReportVO);
                }
            }else {
                logger.info("无指定主机信息");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return systemInfoReportVOList;
    }

    @Override
    public List<SystemInfoReportVO> queryRealTimeInfoList(String startTime) {
        List<SystemInfoReportVO> systemInfoReportVOList = new ArrayList<>();
        try {
            Query query = new Query();
            query.addCriteria(new Criteria("startTime").is(startTime));
            List<SystemInfoReportPO> systemInfoReportPOList;

            systemInfoReportPOList = this.mongoTemplate.find(query,SystemInfoReportPO.class,SYSTEM_INFO_COLLECTION_NAME);

            if (systemInfoReportPOList != null){
                for (SystemInfoReportPO systemInfoReportPO : systemInfoReportPOList){
                    SystemInfoReportVO systemInfoReportVO = systemInfoReportPO.toReportVO();
                    systemInfoReportVOList.add(systemInfoReportVO);
                }
            }else {
                logger.info("当前主机数量为零");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return systemInfoReportVOList;
    }
}
