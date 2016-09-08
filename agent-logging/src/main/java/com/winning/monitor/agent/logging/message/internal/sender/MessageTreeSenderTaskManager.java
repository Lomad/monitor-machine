package com.winning.monitor.agent.logging.message.internal.sender;

import com.winning.monitor.agent.logging.entity.ConfigManager;
import com.winning.monitor.agent.sender.transport.IMessageTransport;
import com.winning.monitor.agent.sender.transport.factory.MessageTransportFactory;

/**
 * Created by nicholasyan on 16/9/8.
 */
public class MessageTreeSenderTaskManager {

    private final ConfigManager configManager;
    private final MessageTransportFactory messageTransportFactory = new MessageTransportFactory();
    private final MessageTreeSenderTask messageTreeSenderTask;
    private final MessageTreeStorage messageTreeStorage;
    private IMessageTransport messageTransport;

    public MessageTreeSenderTaskManager(ConfigManager configManager,
                                        MessageTreeStorage messageTreeStorage) {
        this.configManager = configManager;
        this.messageTreeStorage = messageTreeStorage;
        this.messageTreeSenderTask = new MessageTreeSenderTask(this.messageTreeStorage, 20);
    }

    public void initialize() {
        this.messageTransport = new NettyMessageTransport(configManager.getSenderConfig().getServers());
//                this.messageTransportFactory.createMessageTransport(configManager.getSenderConfig());
        this.messageTreeSenderTask.initialize(this.messageTransport);
    }


    public void start() {
        this.messageTreeSenderTask.start();
    }


    public void shutdown() {
        this.messageTreeSenderTask.shutdown();
    }


}
