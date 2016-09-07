package com.winning.monitor.agent.sender.collect;

import com.winning.monitor.agent.sender.AbstractQueueDataSender;
import com.winning.monitor.agent.sender.ICollectDataSender;
import com.winning.monitor.agent.sender.transport.IMessageTransport;
import com.winning.monitor.message.MessagePackage;
import com.winning.monitor.message.collector.CollectData;
import com.winning.monitor.message.collector.CollectorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by nicholasyan on 16/9/6.
 */
public class DefaultCollectDataSender extends AbstractQueueDataSender<CollectData>
        implements ICollectDataSender {

    private static Logger logger = LoggerFactory.getLogger(DefaultCollectDataSender.class);

    private final IMessageTransport messageTransport;

    public DefaultCollectDataSender(IMessageTransport messageTransport,
                                    int maxQueueSize, int maxEntities) {
        super(maxQueueSize, maxEntities);
        this.messageTransport = messageTransport;
    }

    @Override
    public boolean doSendDataEntities(List<CollectData> dataEntities) {

        //组装数据
        CollectorMessage collectorMessage = new CollectorMessage();
        collectorMessage.addCollectDatas(dataEntities);
        MessagePackage messagePackage = new MessagePackage();
        messagePackage.setMessage(collectorMessage);

        this.messageTransport.sendMessage(messagePackage);

        return true;
    }

    @Override
    public void initialize() {
        logger.info("正在初始化并启动CollectDataSender");
        this.messageTransport.initialize();
        this.start();
    }

    @Override
    public boolean send(CollectData data) {
        return this.put(data);
    }

    @Override
    public void shutdown() {
        logger.info("正在关闭CollectDataSender");
        super.stop();
        this.messageTransport.shutdown();
    }
}
