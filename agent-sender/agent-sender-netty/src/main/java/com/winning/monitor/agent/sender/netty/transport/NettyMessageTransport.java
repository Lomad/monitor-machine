package com.winning.monitor.agent.sender.netty.transport;

import com.winning.monitor.agent.sender.transport.IMessageTransport;
import com.winning.monitor.message.MessagePackage;
import com.winning.monitor.messsage.netty.io.NettyTextMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nicholasyan on 16/9/6.
 */
public class NettyMessageTransport implements IMessageTransport {

    private static Logger logger = LoggerFactory.getLogger(NettyMessageTransport.class);

    private final NettyTextMessageCodec nettyTextMessageCodec = new NettyTextMessageCodec();

    private final NettyChannelManager nettyChannelManager;

    public NettyMessageTransport(String address) {
        nettyChannelManager = new NettyChannelManager(address);
    }

    @Override
    public void initialize() {
        logger.info("正在初始化 NettyMessageTransport...");
    }

    @Override
    public void sendMessage(MessagePackage messagePackage) {


        System.out.println("发送消息" + System.currentTimeMillis());
    }

    @Override
    public void shutdown() {
        logger.info("正在关闭 NettyMessageTransport");
    }
}
