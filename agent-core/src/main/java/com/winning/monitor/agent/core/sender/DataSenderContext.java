package com.winning.monitor.agent.core.sender;


import com.winning.monitor.agent.config.sender.ISenderConfigFactory;
import com.winning.monitor.agent.config.sender.SenderConfig;
import com.winning.monitor.agent.sender.transport.IMessageTransport;
import com.winning.monitor.agent.sender.transport.factory.MessageTransportFactory;

/**
 * Created by nicholasyan on 16/9/7.
 */
public class DataSenderContext {

    private final ISenderConfigFactory senderConfigFactory;
    private final MessageTransportFactory messageTransportFactory;
    private IMessageTransport messageTransport;

    private boolean initialed = false;

    public DataSenderContext(ISenderConfigFactory senderConfigFactory,
                             MessageTransportFactory messageTransportFactory) {
        this.senderConfigFactory = senderConfigFactory;
        this.messageTransportFactory = messageTransportFactory;
    }

    public void initialize() {
        SenderConfig senderConfig = this.senderConfigFactory.getSenderConfig();
        this.messageTransport = this.messageTransportFactory.createMessageTransport(senderConfig);
    }

    public IMessageTransport getMessageTransport() {
        return this.messageTransport;
    }


}
