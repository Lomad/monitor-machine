package com.winning.monitor.superisor.consumer.collector;

import com.winning.monitor.agent.collector.api.entity.CollectData;
import com.winning.monitor.agent.collector.api.entity.CollectDatas;
import com.winning.monitor.superisor.consumer.api.analysis.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CollectDatasMessageHandler implements MessageHandler<CollectDatas>, ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(CollectDatasMessageHandler.class);

    private ApplicationContext applicationContext;

    private Map<String, ICollectDataAnalyzeQueue> collectDataAnalyzeQueneMap;

    @PostConstruct
    public void init() {
        this.collectDataAnalyzeQueneMap = new HashMap<>();
        Map<String, ICollectDataAnalyzeQueue> beans =
                this.applicationContext.getBeansOfType(ICollectDataAnalyzeQueue.class);
        if (beans == null)
            return;

        for (ICollectDataAnalyzeQueue collectDataAnalyzeQuene : beans.values()) {
            this.collectDataAnalyzeQueneMap.put(collectDataAnalyzeQuene.getCollectTypeName(),
                    collectDataAnalyzeQuene);
        }
    }


    @Override
    public String getMessageTypeName() {
        return CollectDatas.MESSAGE_TYPE;
    }

    @Override
    public void handleMessage(CollectDatas collectDatas) {
        String ip = collectDatas.getIpAddress();
        String hostname = collectDatas.getHostName();

        List<CollectData> collectDataList = collectDatas.getDatas();
        if (collectDataList == null || collectDataList.size() == 0)
            return;

        for (CollectData collectData : collectDataList) {
            try {
                String collectorType = collectData.getCollectorType();
                ICollectDataAnalyzeQueue collectDataAnalyzeQuene =
                        this.collectDataAnalyzeQueneMap.get(collectorType);

                if (collectDataAnalyzeQuene == null) {
                    logger.warn("不存在{}对应的CollectDataAnalyzer,消息被抛弃", collectorType);
                    continue;
                }

                collectData.setIpAddress(ip);
                collectData.setHostName(hostname);

                collectDataAnalyzeQuene.offer(collectData);
            } catch (Exception e) {
                logger.error("解析并分发collectData时发生错误", e);
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
