package com.winning.monitor.agent.sender.collect;

import com.winning.monitor.agent.config.utils.NetworkInterfaceManager;
import com.winning.monitor.agent.sender.AbstractDataSenderTask;
import com.winning.monitor.agent.sender.IDataEntitySenderTask;
import com.winning.monitor.agent.sender.IDataEntityStorage;
import com.winning.monitor.agent.sender.transport.IMessageTransport;
import com.winning.monitor.message.MessageHead;
import com.winning.monitor.message.MessagePackage;
import com.winning.monitor.message.collector.CollectData;
import com.winning.monitor.message.collector.CollectorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by nicholasyan on 16/9/6.
 */
public class DefaultCollectDataSenderTask extends AbstractDataSenderTask<CollectData>
        implements IDataEntitySenderTask {

    private static Logger logger = LoggerFactory.getLogger(DefaultCollectDataSenderTask.class);

    private IMessageTransport messageTransport;

    public DefaultCollectDataSenderTask(IDataEntityStorage collectDataStorage,
                                        int maxEntities) {
        super(collectDataStorage, maxEntities);
    }

    @Override
    public void initialize(IMessageTransport messageTransport) {
        logger.info("正在初始化并启动CollectDataSender");
        this.messageTransport = messageTransport;

    }

    @Override
    public boolean doSendDataEntities(List<CollectData> dataEntities) {

        MessageHead head = new MessageHead();
        head.setTimestamp(new Date());
        head.setMessageType(CollectorMessage.TYPE_NAME);
        head.setHostName(NetworkInterfaceManager.INSTANCE.getLocalHostName());
        head.setIpAddress(NetworkInterfaceManager.INSTANCE.getLocalHostAddress());
        head.setMessageId(UUID.randomUUID().toString());

        //组装数据
        CollectorMessage collectorMessage = new CollectorMessage();
        collectorMessage.addCollectDatas(dataEntities);

        MessagePackage messagePackage = new MessagePackage();
        messagePackage.setHead(head);
        messagePackage.setMessage(collectorMessage);

        this.messageTransport.sendMessage(messagePackage);

        return true;
    }

    @Override
    public void shutdown() {
        logger.info("正在关闭CollectDataSender");
        super.stop();
    }
}
