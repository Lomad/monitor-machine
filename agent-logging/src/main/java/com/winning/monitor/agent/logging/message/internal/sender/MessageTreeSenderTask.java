package com.winning.monitor.agent.logging.message.internal.sender;

import com.winning.monitor.agent.config.utils.NetworkInterfaceManager;
import com.winning.monitor.agent.logging.message.MessageTree;
import com.winning.monitor.agent.sender.AbstractDataSenderTask;
import com.winning.monitor.agent.sender.IDataEntitySenderTask;
import com.winning.monitor.agent.sender.transport.IMessageTransport;
import com.winning.monitor.message.MessageHead;
import com.winning.monitor.message.MessagePackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by nicholasyan on 16/9/6.
 */
public class MessageTreeSenderTask extends AbstractDataSenderTask<MessageTree>
        implements IDataEntitySenderTask {

    private static Logger logger = LoggerFactory.getLogger(MessageTreeSenderTask.class);

    private IMessageTransport messageTransport;

    public MessageTreeSenderTask(MessageTreeStorage messageTreeStorage,
                                 int maxEntities) {
        super(messageTreeStorage, maxEntities);
    }

    @Override
    public void initialize(IMessageTransport messageTransport) {
        logger.info("正在初始化并启动 MessageTreeSenderTask");
        this.messageTransport = messageTransport;
    }

    @Override
    public boolean doSendDataEntities(List<MessageTree> dataEntities) {

        for (int i = 0; i < dataEntities.size(); i++) {
            MessageHead head = new MessageHead();
            head.setTimestamp(new Date());
            head.setMessageType(MessageTree.TYPE_NAME);
            head.setHostName(NetworkInterfaceManager.INSTANCE.getLocalHostName());
            head.setIpAddress(NetworkInterfaceManager.INSTANCE.getLocalHostAddress());
            head.setMessageId(UUID.randomUUID().toString());

            MessagePackage messagePackage = new MessagePackage();
            messagePackage.setHead(head);
            messagePackage.setMessage(dataEntities.get(i));

            this.messageTransport.sendMessage(messagePackage);
        }

        return true;
    }

    @Override
    public void shutdown() {
        logger.info("正在关闭CollectDataSender");
        super.stop();
    }
}
