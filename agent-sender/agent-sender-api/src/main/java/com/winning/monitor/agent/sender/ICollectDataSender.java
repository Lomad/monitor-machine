package com.winning.monitor.agent.sender;

import com.winning.monitor.message.collector.CollectData;

/**
 * Created by nicholasyan on 16/9/6.
 */
public interface ICollectDataSender {

    void initialize();

    boolean send(CollectData data);

    void shutdown();

}
