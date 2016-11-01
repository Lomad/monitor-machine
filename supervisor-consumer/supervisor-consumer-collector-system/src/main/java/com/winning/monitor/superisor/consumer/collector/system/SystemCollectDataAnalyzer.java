package com.winning.monitor.superisor.consumer.collector.system;

import com.winning.monitor.agent.collector.api.entity.CollectData;
import com.winning.monitor.superisor.consumer.collector.ICollectDataAnalyzer;

public class SystemCollectDataAnalyzer implements ICollectDataAnalyzer {

    @Override
    public void analyze(CollectData collectData) {

    }

    @Override
    public String getCollectType() {
        return "SystemDynamic";
    }
}
