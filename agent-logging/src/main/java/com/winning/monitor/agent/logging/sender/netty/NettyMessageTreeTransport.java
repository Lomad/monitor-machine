package com.winning.monitor.agent.logging.sender.netty;


import com.winning.monitor.agent.logging.message.MessageTree;
import com.winning.monitor.agent.logging.sender.IMessageTransport;
import com.winning.monitor.agent.logging.storage.MessageTreeStorage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by nicholasyan on 16/9/6.
 */
public class NettyMessageTreeTransport implements IMessageTransport {

    private static Logger logger = LoggerFactory.getLogger(NettyMessageTreeTransport.class);

    private final MessageTreeMessageCodec messageTreeMessageCodec = new MessageTreeMessageCodec();
    private final NettyChannelManager nettyChannelManager;
    private AtomicInteger m_attempts = new AtomicInteger();


    public NettyMessageTreeTransport(String servers, MessageTreeStorage messageTreeStorage) {
        this.nettyChannelManager = new NettyChannelManager(servers, messageTreeStorage);
    }

    @Override
    public void initialize() {
        logger.info("正在初始化 NettyMessageTreeTransport...");
        this.nettyChannelManager.start();
    }

    @Override
    public void sendMessage(MessageTree messageTree) {

//        long current = System.currentTimeMillis();

        if (this.sendDataAvailable()) {

            ByteBuf buf = PooledByteBufAllocator.DEFAULT.buffer(10 * 1024); // 10K

            messageTreeMessageCodec.encode(messageTree, buf);

            int size = buf.readableBytes();

            ChannelFuture future = nettyChannelManager.channel();
            Channel channel = future.channel();
            channel.writeAndFlush(buf);
//            current = System.currentTimeMillis() - current;

//            logger.debug("发送消息长度:" + size + "bytes,用时" + current + "ms");
        }
    }

    private boolean checkWritable(ChannelFuture future) {
        boolean isWriteable = false;
        Channel channel = future.channel();

        if (future != null && channel.isOpen()) {
            if (channel.isActive() && channel.isWritable()) {
                isWriteable = true;
            } else {
                int count = m_attempts.incrementAndGet();

                if (count % 1000 == 0 || count == 1) {
                    logger.error("Netty write buffer is full! Attempts: " + count);
                }
            }
        }

        return isWriteable;
    }

    @Override
    public void shutdown() {
        logger.info("正在关闭 NettyMessageTreeTransport");
        this.nettyChannelManager.shutdown();
    }

    @Override
    public boolean sendDataAvailable() {
        ChannelFuture future = nettyChannelManager.channel();
        if (future != null && checkWritable(future))
            return true;

        return false;
    }
}
