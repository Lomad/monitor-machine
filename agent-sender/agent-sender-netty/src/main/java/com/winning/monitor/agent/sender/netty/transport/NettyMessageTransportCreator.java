package com.winning.monitor.agent.sender.netty.transport;

import com.winning.monitor.agent.config.sender.SenderConfig;
import com.winning.monitor.agent.sender.transport.IMessageTransport;
import com.winning.monitor.agent.sender.transport.factory.IMessageTransportCreator;

/**
 * Created by nicholasyan on 16/9/7.
 */
public class NettyMessageTransportCreator implements IMessageTransportCreator {

    @Override
    public IMessageTransport createMessageTransport(SenderConfig senderConfig) {
        return new NettyMessageTransport(senderConfig.getServers());
    }
}
