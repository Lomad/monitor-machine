package com.winning.monitor.agent.sender.collect;

import com.winning.monitor.agent.sender.AbstractDataStorage;
import com.winning.monitor.agent.sender.IDataEntityStorage;
import com.winning.monitor.message.collector.CollectData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by nicholasyan on 16/9/6.
 */
public class DefaultCollectDataStorage extends AbstractDataStorage<CollectData>
        implements IDataEntityStorage<CollectData> {

    private static Logger logger = LoggerFactory.getLogger(DefaultCollectDataStorage.class);

    public DefaultCollectDataStorage(int maxQueueSize) {
        super(maxQueueSize);
    }

    @Override
    public void initialize() {
        logger.info("正在初始化并启动 CollectDataStorage");
    }

    @Override
    public boolean put(CollectData data) {
        return this.offer(data);
    }

    @Override
    public List<CollectData> get(int size) {
        return super.poll(size);
    }

    @Override
    public int remainSize() {
        return dataEntityQueue.size();
    }

    @Override
    public void shutdown() {
        logger.info("正在关闭 CollectDataStorage");
        super.shutdown();
    }
}
