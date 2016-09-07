package com.winning.monitor.agent.core.collector;

import com.winning.monitor.agent.collector.api.CollectorExecutorFactory;
import com.winning.monitor.agent.collector.api.executor.DataCollectExecutor;
import com.winning.monitor.agent.config.collector.CollectorConfig;
import com.winning.monitor.agent.config.collector.ICollectorConfigFactory;
import com.winning.monitor.agent.sender.ICollectDataSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nicholasyan on 16/9/5.
 */
public class CollectExecutorContext {

    private final ICollectorConfigFactory collectorConfigFactory;
    private final ICollectDataSender collectDataSender;
    private final CollectorExecutorFactory collectorExecutorFactory;
    private final CollectExecutorTaskManager collectExecutorTaskManager;

    private final Map<String, DataCollectExecutor> dataCollectExecutorMap =
            new HashMap<>();

    private List<CollectExecutorTask> executorTasks = new ArrayList<>();
    private boolean initialed = false;

    public CollectExecutorContext(ICollectorConfigFactory collectorConfigFactory,
                                  CollectorExecutorFactory collectorExecutorFactory,
                                  CollectExecutorTaskManager collectExecutorTaskManager,
                                  ICollectDataSender collectDataSender) {

        this.collectorConfigFactory = collectorConfigFactory;
        this.collectorExecutorFactory = collectorExecutorFactory;
        this.collectExecutorTaskManager = collectExecutorTaskManager;

        this.collectDataSender = collectDataSender;

        //开始初始化
        this.collectDataSender.initialize();
    }


    /**
     * 初始化收集器上下文
     */
    public void initialContext() {
        if (!this.initialed) {
            //初始化收集器
            if (this.collectorConfigFactory.getCollectorConfigs() != null) {
                for (CollectorConfig collectorConfig : this.collectorConfigFactory.getCollectorConfigs()) {
                    this.initCollectorConfig(collectorConfig);
                }
            }
        }
        this.initialed = true;
    }

    private void initCollectorConfig(CollectorConfig collectorConfig) {
        DataCollectExecutor dataCollectExecutor =
                this.collectorExecutorFactory.createDataCollectExecutor(collectorConfig);

        this.dataCollectExecutorMap.put(collectorConfig.getCollectorName(), dataCollectExecutor);

        CollectExecutorTask collectExecutorTask = new CollectExecutorTask(dataCollectExecutor,
                this.collectDataSender);

        this.executorTasks.add(collectExecutorTask);
        this.collectExecutorTaskManager.addCollectExecutorTask(collectExecutorTask);
    }

    public void start() {
        this.initialContext();
        this.collectExecutorTaskManager.start();
    }

    public void shutdown() {
        this.collectDataSender.shutdown();
    }

}
