package com.winning.monitor.superisor.consumer.collector;

import com.winning.monitor.agent.collector.api.entity.CollectData;

/**
 * Created by nicholasyan on 16/10/14.
 */
public interface ICollectDataAnalyzer {

    void analyze(CollectData collectData);

    String getCollectType();

}
