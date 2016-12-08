package com.winning.monitor.supervisor.consumer.machine;

import com.winning.monitor.agent.collector.api.entity.CollectDatas;
import com.winning.monitor.superisor.consumer.api.analysis.MessageAnalyzer;
import com.winning.monitor.superisor.consumer.api.analysis.MessageHandler;
import com.winning.monitor.supervisor.core.period.Period;
import com.winning.monitor.supervisor.core.period.PeriodManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

/**
 * @Author Lemod
 * @Version 2016/12/6
 */
@Service
public class CollectDataMessageHandler implements MessageHandler<CollectDatas> {

    private static Logger logger = LoggerFactory.getLogger(CollectDataMessageHandler.class);

    private static final long HOUR = 60 * 60 * 1000L;

    @Autowired
    private PeriodManager periodManager;

    @Override
    public String getMessageTypeName() {
        return CollectDatas.MESSAGE_TYPE;
    }

    @Override
    public void handleMessage(CollectDatas message) {

        long timestamp = System.currentTimeMillis();
        Period period = periodManager.findPeriod(timestamp);

        if (period != null){
            period.distribute(message);
        }else {
            logger.error("消息处理时间有误");
        }

    }

    @PreDestroy
    public void doCheckpoint(){
        long currentTime = this.getCurrentTime();

        Period currentPeriod = periodManager.findPeriod(currentTime);

        try {
            for (MessageAnalyzer messageAnalyzer : currentPeriod.getAnalzyers()) {
                try {
                    messageAnalyzer.doCheckpoint(false);
                } catch (Exception e) {
                    logger.error("销毁分析器异常");
                }
            }

            Thread.sleep(100);

        }catch (Exception e){
            logger.error("销毁收集器异常");
        }
    }

    private Long getCurrentTime(){
        long temp = System.currentTimeMillis();
        long time = temp - temp % HOUR;

        return time;
    }
}
