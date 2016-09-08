package com.winning.monitor.agent.logging.message.internal.sender;


import com.winning.monitor.agent.sender.transport.IMessageTransport;
import com.winning.monitor.message.MessagePackage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created by nicholasyan on 16/9/6.
 */
public class NettyMessageTransport implements IMessageTransport {

    private static Logger logger = LoggerFactory.getLogger(NettyMessageTransport.class);

    private final MessageTreeMessageCodec messageTreeMessageCodec = new MessageTreeMessageCodec();
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

        long current = System.currentTimeMillis();

        ChannelFuture future = nettyChannelManager.getChannelFuture();

        ByteBuf buf = PooledByteBufAllocator.DEFAULT.buffer(10 * 1024); // 10K

        messageTreeMessageCodec.encodeMessage(messagePackage, buf);

        int size = buf.readableBytes();
        Channel channel = future.channel();
        channel.writeAndFlush(buf);

        current = System.currentTimeMillis() - current;

        System.out.println("发送消息长度:" + size + ",用时" + current + "ms");
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = null;
//        try {
//            body = new String(req, "UTF-8");
//            System.out.println(body);
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void shutdown() {
        logger.info("正在关闭 NettyMessageTransport");
    }
}
